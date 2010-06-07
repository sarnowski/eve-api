/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.account;

import com.eveonline.api.LimitedApiKey;
import com.eveonline.api.account.Characters;
import com.eveonline.api.account.CharactersApi;
import com.eveonline.api.exceptions.ApiException;
import com.google.inject.Inject;
import org.onsteroids.eve.api.cache.ApiCache;
import org.onsteroids.eve.api.connector.ApiConnection;
import org.onsteroids.eve.api.provider.AbstractApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
final class CharactersApiImpl extends AbstractApiService implements CharactersApi {
	private static final Logger LOG = LoggerFactory.getLogger(CharactersApiImpl.class);

	@Inject
	public CharactersApiImpl(ApiConnection apiConnection, ApiCache apiCache) {
		super(apiConnection, apiCache);
	}

	@Override
	public Characters getCharacters(LimitedApiKey key) throws ApiException {
		return call(CharactersImpl.class, CharactersApi.XMLPATH, key);
	}
}
