/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.account;

import com.eveonline.api.account.CharactersApi;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class AccountModule implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(AccountModule.class);

	@Override
	public void configure(Binder binder) {
		binder.bind(CharactersApi.class).to(CharactersApiImpl.class).in(Scopes.NO_SCOPE);
	}
}
