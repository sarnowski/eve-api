package org.onsteroids.eve.api.examples.javaee;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.server.ServerStatus;
import com.eveonline.api.server.ServerStatusApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Tobias Sarnowski
 */
@Named("serverStatus")
@RequestScoped
public class ServerStatusController {
	private static final Logger LOG = LoggerFactory.getLogger(ServerStatusController.class);

	@Inject
	private ServerStatusApi serverStatusApi;

	private ServerStatus serverStatus;


	private void retrieveServerStatus() {
		// get it once per request, see @RequestScoped
		if (serverStatus == null) {
			try {
				LOG.info("Getting ServerStatus from EVE API server...");
				serverStatus = serverStatusApi.getServerStatus();
			} catch (ApiException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	public long getOnlinePlayers() {
		retrieveServerStatus();

		return serverStatus.getOnlinePlayers();
	}

	public boolean getIsServerOpen() {
		retrieveServerStatus();

		return serverStatus.isServerOpen();
	}
}
