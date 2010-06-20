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

/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider;

import com.eveonline.api.ApiKey;
import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.server.ServerStatusApi;
import com.google.common.collect.Maps;
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
			XmlApiResult result = apiConnection.call(xmlPath, key, parameters);
			try {
				cached = resultType.newInstance();
			} catch (InstantiationException e) {
				throw new InternalApiException(e);
			} catch (IllegalAccessException e) {
				throw new InternalApiException(e);
			}
			cached.processCoreResult(result, result.getResult());

			// put into the cache for future usage
			apiCache.putApiResult(cached);
		}
		return cached;
	}

	/**
	 * @param key
	 * @param value
	 * @return a map with an entry of key-value
	 */
	public Map<String,String> withParameter(String key, String value) {
		Map<String,String> map = Maps.newHashMap();
		map.put(key, value);
		return map;
	}

	/**
	 * @param key1
	 * @param value1
	 * @param key2
	 * @param value2
	 * @return a map with two entries of key1-value1 and key2-value2
	 */
	public Map<String,String> withParameter(String key1, String value1, String key2, String value2) {
		Map<String,String> map = Maps.newHashMap();
		map.put(key1, value1);
		map.put(key2, value2);
		return map;
	}

}
