package org.onsteroids.eve.api.cache;

import com.eveonline.api.ApiKey;
import com.eveonline.api.ApiResult;
import com.google.inject.Inject;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Tobias Sarnowski
 */
class InfinispanApiCache implements ApiCache {
	private static final Logger LOG = LoggerFactory.getLogger(InfinispanApiCache.class);

	private final EmbeddedCacheManager cacheManager;
	private final Cache<CacheKey,ApiResult> cache;

	@Inject
	public InfinispanApiCache() {
		cacheManager = new DefaultCacheManager();
		cache = cacheManager.getCache();
	}

	@Override
	public void putApiResult(ApiResult apiResult) {
		long remainsCached = apiResult.getCachedUntil().getTime() - System.currentTimeMillis();
		if (remainsCached > 0) {
			cache.putAsync(new CacheKey(apiResult), apiResult, remainsCached, TimeUnit.MILLISECONDS);
		}
	}

	@Override
	public <T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath) {
		return getApiResult(resultType, serverUri, xmlPath, null, null);
	}

	@Override
	public <T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath, Map<String, String> parameters) {
		return getApiResult(resultType, serverUri, xmlPath, null, parameters);
	}

	@Override
	public <T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath, ApiKey key) {
		return getApiResult(resultType, serverUri, xmlPath, key, null);
	}

	@Override
	public <T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath, ApiKey key, Map<String, String> parameters) {
		ApiResult result = cache.get(new CacheKey(serverUri, xmlPath, key, parameters));
		return result == null ? null : resultType.cast(result);
	}


	static class CacheKey implements Serializable {
		private URI serverUri;
		private String xmlPath;
		private ApiKey apiKey;
		private Map<String, String> parameters;

		CacheKey(ApiResult apiResult) {
			serverUri = apiResult.getUsedServerUri();
			xmlPath = apiResult.getRequestedXmlPath();
			apiKey = apiResult.getUsedApiKey();
			parameters = apiResult.getUsedParameters();
		}

		CacheKey(URI serverUri, String xmlPath, ApiKey apiKey, Map<String, String> parameters) {
			this.serverUri = serverUri;
			this.xmlPath = xmlPath;
			this.apiKey = apiKey;
			this.parameters = parameters;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			CacheKey cacheKey = (CacheKey) o;

			if (apiKey != null ? !apiKey.equals(cacheKey.apiKey) : cacheKey.apiKey != null) return false;
			if (parameters != null ? !parameters.equals(cacheKey.parameters) : cacheKey.parameters != null)
				return false;
			if (serverUri != null ? !serverUri.equals(cacheKey.serverUri) : cacheKey.serverUri != null) return false;
			if (xmlPath != null ? !xmlPath.equals(cacheKey.xmlPath) : cacheKey.xmlPath != null) return false;

			return true;
		}

		@Override
		public int hashCode() {
			int result = serverUri != null ? serverUri.hashCode() : 0;
			result = 31 * result + (xmlPath != null ? xmlPath.hashCode() : 0);
			result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
			result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
			return result;
		}
	}
}
