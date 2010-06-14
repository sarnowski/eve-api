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

package org.onsteroids.eve.api.provider.map;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.map.FacWarSystems;
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
public final class FacWarSystemsImpl extends SerializableApiListResult<FacWarSystemsImpl.SolarSystemImpl> implements FacWarSystems<FacWarSystemsImpl.SolarSystemImpl> {
    private static final Logger LOG = LoggerFactory.getLogger(FacWarSystemsImpl.class);

    @Override
    public Class<? extends SolarSystemImpl> getRowDefinition() {
        return SolarSystemImpl.class;
    }


    public static final class SolarSystemImpl extends SerializableApiResult implements FacWarSystems.SolarSystem {

        private long id;
        private String name;
        private long occupyingFactionId;
        private String occupyingFactionName;
        private boolean contested;

        @Override
        public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
            XmlUtility xml = new XmlUtility(xmlResult);

            id = Long.parseLong(xml.getAttribute("solarSystemID"));
            name = xml.getAttribute("solarSystemName");
            occupyingFactionId = Long.parseLong(xml.getAttribute("occupyingFactionID"));
            occupyingFactionName = xml.getAttribute("occupyingFactionName");
            contested = "True".equalsIgnoreCase(xml.getAttribute("contested"));
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
        public long getOccupyingFactionId() {
            return occupyingFactionId;
        }

        @Override
        public String getOccupyingFactionName() {
            return occupyingFactionName;
        }

        @Override
        public boolean isContested() {
            return contested;
        }
    }
}