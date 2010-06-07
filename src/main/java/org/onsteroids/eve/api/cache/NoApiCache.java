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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
class NoApiCache implements ApiCache {
	private static final Logger LOG = LoggerFactory.getLogger(NoApiCache.class);

	@Override
	public void putApiResult(ApiResult apiResult) {
	}

	@Override
	public <T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath) {
		return null;
	}

	@Override
	public <T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath, Map<String, String> parameters) {
		return null;
	}

	@Override
	public <T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath, ApiKey key) {
		return null;
	}

	@Override
	public <T extends ApiResult> T getApiResult(Class<T> resultType, URI serverUri, String xmlPath, ApiKey key, Map<String, String> parameters) {
		return null;
	}
}
