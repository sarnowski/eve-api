/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.connector.http;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import org.onsteroids.eve.api.connector.ApiConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class PooledHttpApiConnectionModule implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(PooledHttpApiConnectionModule.class);

	public void configure(Binder binder) {
		binder.bind(ApiConnection.class).to(PooledHttpApiConnection.class).in(Singleton.class);
	}

}
