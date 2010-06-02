/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.account;

import com.eveonline.api.ApiListResult;
import com.eveonline.api.LimitedApiKey;
import com.eveonline.api.account.Character;
import com.eveonline.api.account.CharactersApi;
import com.eveonline.api.exceptions.ApiException;
import com.google.common.base.Function;
import com.google.inject.Inject;
import org.onsteroids.eve.api.connector.ApiConnection;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.onsteroids.eve.api.provider.AbstractApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * @author Tobias Sarnowski
 */
class CharactersApiImpl extends AbstractApiService implements CharactersApi {
	private static final Logger LOG = LoggerFactory.getLogger(CharactersApiImpl.class);

	private static final String XMLPATH_CHARACTERS = "/account/Characters.xml.aspx";

	private ApiConnection apiConnection;

	@Inject
	public CharactersApiImpl(ApiConnection apiConnection) {
		this.apiConnection = apiConnection;
	}

	@Override
	public ApiListResult<Character> getCharacters(LimitedApiKey key) throws ApiException {
		final XmlApiResult result = apiConnection.call(XMLPATH_CHARACTERS, key);
		return getRowset(result.getResult(), new Function<Node, Character>() {
			@Override
			public Character apply(Node from) {
				return new CharacterImpl(result, from);
			}
		});
	}
}
