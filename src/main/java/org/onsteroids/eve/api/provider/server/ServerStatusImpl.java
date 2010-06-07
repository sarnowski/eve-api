/**
 * Copyright 2010 Tobias Sarnowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.server;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.server.ServerStatus;
import org.onsteroids.eve.api.XmlUtility;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.onsteroids.eve.api.provider.SerializableApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * @author Tobias Sarnowski
 */
public final class ServerStatusImpl extends SerializableApiResult implements ServerStatus {
	private static final Logger LOG = LoggerFactory.getLogger(ServerStatusImpl.class);

	private boolean isServerOpen;
	private long onlinePlayers;

	@Override
	public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
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
