/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.server;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.server.ServerStatus;
import com.eveonline.api.server.ServerStatusApi;
import com.google.inject.Inject;
import org.onsteroids.eve.api.cache.ApiCache;
import org.onsteroids.eve.api.connector.ApiConnection;
import org.onsteroids.eve.api.provider.AbstractApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
final class ServerStatusApiImpl extends AbstractApiService implements ServerStatusApi {
	private static final Logger LOG = LoggerFactory.getLogger(ServerStatusApiImpl.class);

	@Inject
	public ServerStatusApiImpl(ApiConnection apiConnection, ApiCache apiCache) {
		super(apiConnection, apiCache);
	}

	public ServerStatus getServerStatus() throws ApiException {
		return call(ServerStatusImpl.class, ServerStatusApi.XMLPATH);
	}
}
