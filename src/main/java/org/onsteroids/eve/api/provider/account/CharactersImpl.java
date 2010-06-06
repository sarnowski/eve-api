/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.account;

import com.eveonline.api.account.Characters;
import org.onsteroids.eve.api.XmlUtility;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.onsteroids.eve.api.provider.SerializableApiListResult;
import org.onsteroids.eve.api.provider.SerializableApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * @author Tobias Sarnowski
 */
class CharactersImpl extends SerializableApiListResult<CharactersImpl.CharacterImpl> implements Characters {
	private static final Logger LOG = LoggerFactory.getLogger(CharactersImpl.class);
	

	@Override
	public Class<? extends CharacterImpl> getRowDefinition() {
		return CharacterImpl.class;
	}

	public static class CharacterImpl extends SerializableApiResult implements Characters.Character {

		private long id;
		private String name;
		private long corporationId;
		private String corporationName;

		@Override
		public void processResult(XmlApiResult xmlApiResult, Node xmlResult) {
			XmlUtility xml = new XmlUtility(xmlResult);

			id = Long.parseLong(xml.getAttribute("characterID"));
			name = xml.getAttribute("name");
			corporationId = Long.parseLong(xml.getAttribute("corporationID"));
			corporationName = xml.getAttribute("corporationName");
		}

		@Override
		public long getId() {
			return id;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public long getCorporationId() {
			return corporationId;
		}

		@Override
		public String getCorporationName() {
			return corporationName;
		}
	}
}
