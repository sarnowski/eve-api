/**
 * Copyright 2010 Tobias Sarnowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onsteroids.eve.api.cache;

import com.eveonline.api.ApiKey;
import com.eveonline.api.ApiResult;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Tobias Sarnowski
 */
@Singleton
final class DefaultInfinispanApiCache implements ApiCache {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultInfinispanApiCache.class);

	private final Cache<ApiCacheKey,ApiResult> cache;

	@Inject
	public DefaultInfinispanApiCache(@InfinispanApiCache Cache cache) {
        this.cache = cache;
	}

	@Override
	public void putApiResult(ApiResult apiResult) {
		long remainsCached = apiResult.getCachedUntil().getTime() - System.currentTimeMillis();
		if (remainsCached > 0) {
			cache.putAsync(new ApiCacheKey(apiResult), apiResult, remainsCached, TimeUnit.MILLISECONDS);
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
		ApiResult result = cache.get(new ApiCacheKey(serverUri, xmlPath, key, parameters));
		return result == null ? null : resultType.cast(result);
	}


}
