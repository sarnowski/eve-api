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

package org.onsteroids.eve.api.provider.corp;

import com.eveonline.api.DirectorApiKey;
import com.eveonline.api.corp.StarbaseDetail;
import com.eveonline.api.corp.StarbaseDetailApi;
import com.eveonline.api.exceptions.ApiException;
import org.onsteroids.eve.api.cache.ApiCache;
import org.onsteroids.eve.api.connector.ApiConnection;
import org.onsteroids.eve.api.provider.AbstractApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Tobias Sarnowski
 */
@Singleton
final class DefaultStarbaseDetailApi extends AbstractApiService implements StarbaseDetailApi {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultStarbaseDetailApi.class);

	@Inject
	public DefaultStarbaseDetailApi(ApiConnection apiConnection, ApiCache apiCache) {
		super(apiConnection, apiCache);
	}

	@Override
	public StarbaseDetail getStarbaseDetail(DirectorApiKey apiKey, long characterId, long starbaseId) throws ApiException {
		return call(DefaultStarbaseDetail.class, StarbaseDetailApi.XMLPATH, apiKey, withParameter("characterID", Long.toString(characterId), "itemID", Long.toString(starbaseId)));
	}
}
