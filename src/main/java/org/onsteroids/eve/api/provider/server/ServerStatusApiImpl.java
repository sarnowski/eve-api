/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.server;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.server.ServerStatus;
import com.eveonline.api.server.ServerStatusApi;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.onsteroids.eve.api.connector.ApiConnection;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
class ServerStatusApiImpl implements ServerStatusApi {
	private static final Logger LOG = LoggerFactory.getLogger(ServerStatusApiImpl.class);

	private static final String XMLPATH_SERVERSTATUS = "/server/ServerStatus.xml.aspx";

	private Provider<ApiConnection> apiConnectionProvider;

	@Inject
	public ServerStatusApiImpl(Provider<ApiConnection> apiConnectionProvider) {
		this.apiConnectionProvider = apiConnectionProvider;
	}

	public ServerStatus getServerStatus() throws ApiException {
		ApiConnection connection = apiConnectionProvider.get();
		XmlApiResult result = connection.call(XMLPATH_SERVERSTATUS);
		return new ServerStatusImpl(result);
	}
}
