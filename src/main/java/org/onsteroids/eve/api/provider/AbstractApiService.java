/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider;

import com.eveonline.api.ApiKey;
import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.server.ServerStatusApi;
import com.google.inject.Inject;
import org.onsteroids.eve.api.InternalApiException;
import org.onsteroids.eve.api.cache.ApiCache;
import org.onsteroids.eve.api.connector.ApiConnection;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public abstract class AbstractApiService {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractApiService.class);
	private ApiConnection apiConnection;
	private ApiCache apiCache;

	@Inject
	public AbstractApiService(ApiConnection apiConnection, ApiCache apiCache) {
		this.apiConnection = apiConnection;
		this.apiCache = apiCache;
	}


	/**
	 * @param xmlPath the xml path
	 * @param <T> an api result type
	 * @return the api result
	 */
	public <T extends SerializableApiResult> T call(Class<T> resultType, String xmlPath) throws ApiException {
		return call(resultType, xmlPath, null, null);
	}

	/**
	 * @param xmlPath the xml path
	 * @param parameters the used parameters (can be null)
	 * @param <T> an api result type
	 * @return the cached api result or null if apiresult not cached
	 */
	public <T extends SerializableApiResult> T call(Class<T> resultType, String xmlPath, Map<String,String> parameters) throws ApiException {
		return call(resultType, xmlPath, null, parameters);
	}

	/**
	 * @param xmlPath the xml path
	 * @param key the used api key (can be null)
	 * @param <T> an api result type
	 * @return the cached api result or null if apiresult not cached
	 */
	public <T extends SerializableApiResult> T call(Class<T> resultType, String xmlPath, ApiKey key) throws ApiException {
	    return call(resultType, xmlPath, key, null);
	}

	/**
	 * @param xmlPath the xml path
	 * @param key the used api key (can be null)
	 * @param parameters the used parameters (can be null)
	 * @param <T> an api result type
	 * @return the cached api result or null if apiresult not cached
	 */
	public <T extends SerializableApiResult> T call(Class<T> resultType, String xmlPath, ApiKey key, Map<String,String> parameters) throws ApiException {
		T cached = apiCache.getApiResult(resultType, apiConnection.getServerUri(), ServerStatusApi.XMLPATH);
		if (cached == null) {
			XmlApiResult result = apiConnection.call(xmlPath);
			try {
				cached = resultType.newInstance();
			} catch (InstantiationException e) {
				throw new InternalApiException(e);
			} catch (IllegalAccessException e) {
				throw new InternalApiException(e);
			}
			cached.processCoreResult(result);

			// put into the cache for future usage
			apiCache.putApiResult(cached);
		}
		return cached;
	}
}
