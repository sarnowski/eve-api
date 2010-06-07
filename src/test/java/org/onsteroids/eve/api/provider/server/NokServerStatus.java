/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.server;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.server.ServerStatus;
import com.eveonline.api.server.ServerStatusApi;
import org.junit.Test;
import org.onsteroids.eve.api.provider.AbstractApiTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class NokServerStatus extends AbstractApiTest {
	private static final Logger LOG = LoggerFactory.getLogger(NokServerStatus.class);

	@Test
	public void retrieveServerStatus() throws ApiException {
		ServerStatusApi serverStatusApi = getService(ServerStatusApi.class);
		ServerStatus serverStatus = serverStatusApi.getServerStatus();

		if (serverStatus.isServerOpen()) {
			LOG.info("Tranquility is running with {} players!", serverStatus.getOnlinePlayers());
		} else {
			LOG.info("Tranquility is not running!");
		}
	}
}
