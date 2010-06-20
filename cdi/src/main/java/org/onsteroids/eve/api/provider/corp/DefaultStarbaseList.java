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

package org.onsteroids.eve.api.provider.corp;

import com.eveonline.api.corp.StarbaseList;
import com.eveonline.api.exceptions.ApiException;
import org.onsteroids.eve.api.DateUtility;
import org.onsteroids.eve.api.XmlUtility;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.onsteroids.eve.api.provider.SerializableApiListResult;
import org.onsteroids.eve.api.provider.SerializableApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Tobias Sarnowski
 */
public class DefaultStarbaseList extends SerializableApiListResult<DefaultStarbaseList.DefaultStarbase> implements StarbaseList<DefaultStarbaseList.DefaultStarbase> {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultStarbaseList.class);

	@Override
	public Class<? extends DefaultStarbase> getRowDefinition() {
		return DefaultStarbase.class;
	}

	public static class DefaultStarbase extends SerializableApiResult implements StarbaseList.Starbase {
		private long id;
		private long typeID;
		private long locationId;
		private long moonId;
		private int state;
		private Date stateTime;
		private Date onlineTime;

		@Override
		public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
			XmlUtility xml = new XmlUtility(xmlResult);

			id = Long.parseLong(xml.getAttribute("itemID"));
			typeID = Long.parseLong(xml.getAttribute("typeID"));
			locationId = Long.parseLong(xml.getAttribute("locationID"));
			moonId = Long.parseLong(xml.getAttribute("moonID"));
			state = Integer.parseInt(xml.getAttribute("state"));
			stateTime = DateUtility.parse(xml.getAttribute("stateTimestamp"), xmlApiResult.getTimeDifference(), TimeUnit.MILLISECONDS);
			onlineTime = DateUtility.parse(xml.getAttribute("onlineTimestamp"), xmlApiResult.getTimeDifference(), TimeUnit.MILLISECONDS);
		}

		@Override
		public long getId() {
			return id;
		}

		@Override
		public long getTypeId() {
			return typeID;
		}

		@Override
		public long getLocationId() {
			return locationId;
		}

		@Override
		public long getMoonId() {
			return moonId;
		}

		@Override
		public int getState() {
			return state;
		}

		@Override
		public Date getStateTime() {
			return stateTime;
		}

		@Override
		public Date getOnlineTime() {
			return onlineTime;
		}
	}
}
