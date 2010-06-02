/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.server;

import com.eveonline.api.server.ServerStatus;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.onsteroids.eve.api.provider.AbstractApiResult;
import org.onsteroids.eve.api.provider.XmlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
class ServerStatusImpl extends AbstractApiResult implements ServerStatus {
	private static final Logger LOG = LoggerFactory.getLogger(ServerStatusImpl.class);

	private final XmlUtility xml;

	public ServerStatusImpl(XmlApiResult apiResult) {
		super(apiResult);
		xml = new XmlUtility(apiResult.getResult());
	}

	public boolean isServerOpen() {
		return "True".equalsIgnoreCase(xml.getContentOf("serverOpen"));
	}

	public long getOnlinePlayers() {
		return Long.parseLong(xml.getContentOf("onlinePlayers"));
	}
}
