/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.server;

import com.eveonline.api.server.ServerStatusApi;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public final class ServerModule implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(ServerModule.class);

	public void configure(Binder binder) {
		binder.bind(ServerStatusApi.class).to(ServerStatusApiImpl.class).in(Singleton.class);
	}
}
