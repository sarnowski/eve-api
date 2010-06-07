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

import java.net.URI;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public interface ApiCache {

	/**
	 * @param apiResult the api result to cache
	 */
	void putApiResult(ApiResult apiResult);

	/**
	 * @param serverUri the api server uri
	 * @param xmlPath the xml path
	 * @param <T> an api result type
	 * @return the cached api result or null if apiresult not cached
	 */
	<T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath);

	/**
	 * @param serverUri the api server uri
	 * @param xmlPath the xml path
	 * @param parameters the used parameters (can be null)
	 * @param <T> an api result type
	 * @return the cached api result or null if apiresult not cached
	 */
	<T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath, Map<String,String> parameters);

	/**
	 * @param serverUri the api server uri
	 * @param xmlPath the xml path
	 * @param key the used api key (can be null)
	 * @param <T> an api result type
	 * @return the cached api result or null if apiresult not cached
	 */
	<T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath, ApiKey key);

	/**
	 * @param serverUri the api server uri
	 * @param xmlPath the xml path
	 * @param key the used api key (can be null)
	 * @param parameters the used parameters (can be null)
	 * @param <T> an api result type
	 * @return the cached api result or null if apiresult not cached
	 */
	<T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath, ApiKey key, Map<String,String> parameters);

}
