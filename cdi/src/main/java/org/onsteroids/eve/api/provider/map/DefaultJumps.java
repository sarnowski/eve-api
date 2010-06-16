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
import com.eveonline.api.map.Jumps;
import org.onsteroids.eve.api.DateUtility;
import org.onsteroids.eve.api.InternalApiException;
import org.onsteroids.eve.api.XmlUtility;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.onsteroids.eve.api.provider.SerializableApiListResult;
import org.onsteroids.eve.api.provider.SerializableApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Tobias Sarnowski
 */
public final class DefaultJumps extends SerializableApiListResult<DefaultJumps.SolarSystemImpl> implements Jumps<DefaultJumps.SolarSystemImpl> {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultJumps.class);

    private Date dataTime;

    @Override
    public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
        super.processResult(xmlApiResult, xmlResult);

        XmlUtility xml = new XmlUtility(xmlResult);
        try {
            dataTime = DateUtility.parse(xml.getContentOf("dataTime"), xmlApiResult.getTimeDifference(), TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            throw new InternalApiException(e);
        }
    }

    @Override
    public Date getDataTime() {
        return dataTime;
    }

    @Override
    public Class<? extends SolarSystemImpl> getRowDefinition() {
        return SolarSystemImpl.class;
    }


    public static final class SolarSystemImpl extends SerializableApiResult implements Jumps.SolarSystem {

        private long id;
        private int jumps;

        @Override
        public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
            XmlUtility xml = new XmlUtility(xmlResult);

            id = Long.parseLong(xml.getAttribute("solarSystemID"));
            jumps = Integer.parseInt(xml.getAttribute("shipJumps"));
        }

        @Override
        public long getId() {
            return id;
        }

        @Override
        public int getShipJumps() {
            return jumps;
        }
    }
}