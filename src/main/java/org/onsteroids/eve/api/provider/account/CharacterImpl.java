/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.account;

import com.eveonline.api.account.Characters;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.onsteroids.eve.api.provider.AbstractApiResult;
import org.onsteroids.eve.api.provider.XmlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * @author Tobias Sarnowski
 */
class CharacterImpl extends AbstractApiResult implements Characters.Character {
	private static final Logger LOG = LoggerFactory.getLogger(CharacterImpl.class);

	private final XmlUtility xml;

	public CharacterImpl(XmlApiResult apiResult, Node subNode) {
		super(apiResult, subNode);
		xml = new XmlUtility(subNode);
	}

	@Override
	public long getId() {
		return Long.parseLong(xml.getAttribute("characterID"));
	}

	@Override
	public String getName() {
		return xml.getAttribute("name");
	}

	@Override
	public long getCorporationId() {
		return Long.parseLong(xml.getAttribute("corporationID"));
	}

	@Override
	public String getCorporationName() {
		return xml.getAttribute("corporationName");
	}
}
