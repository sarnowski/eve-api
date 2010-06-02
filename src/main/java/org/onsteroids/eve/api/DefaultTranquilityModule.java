/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.onsteroids.eve.api.connector.ApiConnectionModule;
import org.onsteroids.eve.api.connector.TranquilityModule;
import org.onsteroids.eve.api.provider.ApiServicesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class DefaultTranquilityModule implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultTranquilityModule.class);

	@Override
	public void configure(Binder binder) {
		binder.install(new TranquilityModule());
		binder.install(new ApiConnectionModule());
		binder.install(new ApiServicesModule());
	}
}
