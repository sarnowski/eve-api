/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.account;

import com.eveonline.api.account.Characters;
import com.eveonline.api.account.CharactersApi;
import com.eveonline.api.exceptions.ApiException;
import com.google.common.base.Preconditions;
import org.junit.Test;
import org.onsteroids.eve.api.provider.AbstractApiTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Tobias Sarnowski
 */
public class LimitedCharacters extends AbstractApiTest {
	private static final Logger LOG = LoggerFactory.getLogger(LimitedCharacters.class);

	@Test
	public void retrieveCharacters() throws ApiException {
		CharactersApi charactersApi = getService(CharactersApi.class);
		List<Characters.Character> characters = Preconditions.checkNotNull(charactersApi.getCharacters(getLimitedApiKey()), "Characters");

		for (Characters.Character character: characters) {
			LOG.info("Found Character:  {} ({}) [{} ({})]", new Object[]{
					character.getName(),
					character.getId(),
					character.getCorporationName(),
					character.getCorporationId()
			});
		}
	}
}
