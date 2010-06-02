/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.onsteroids.eve.api.provider.account.AccountModule;
import org.onsteroids.eve.api.provider.server.ServerModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class ApiServicesModule implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(ApiServicesModule.class);

	public void configure(Binder binder) {
		binder.install(new AccountModule());
		binder.install(new ServerModule());
	}
}
