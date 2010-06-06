/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.server;

import com.eveonline.api.server.ServerStatus;
import org.onsteroids.eve.api.XmlUtility;
import org.onsteroids.eve.api.provider.SerializableApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * @author Tobias Sarnowski
 */
class ServerStatusImpl extends SerializableApiResult implements ServerStatus {
	private static final Logger LOG = LoggerFactory.getLogger(ServerStatusImpl.class);

	private boolean isServerOpen;
	private long onlinePlayers;

	@Override
	public void processResult(Node xmlResult) {
		XmlUtility xml = new XmlUtility(xmlResult);

		isServerOpen = "True".equalsIgnoreCase(xml.getContentOf("serverOpen"));
		onlinePlayers = Long.parseLong(xml.getContentOf("onlinePlayers"));
	}

	public boolean isServerOpen() {
		return isServerOpen;
	}

	public long getOnlinePlayers() {
		return onlinePlayers;
	}
}
