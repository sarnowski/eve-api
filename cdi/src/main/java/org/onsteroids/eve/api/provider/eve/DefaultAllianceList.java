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

package org.onsteroids.eve.api.provider.eve;

import com.eveonline.api.ApiListResult;
import com.eveonline.api.eve.AllianceList;
import com.eveonline.api.exceptions.ApiException;
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
public class DefaultAllianceList extends SerializableApiListResult<DefaultAllianceList.AllianceImpl> implements AllianceList<DefaultAllianceList.AllianceImpl> {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultAllianceList.class);

    @Override
    public Class<? extends AllianceImpl> getRowDefinition() {
        return DefaultAllianceList.AllianceImpl.class;
    }

    public static class AllianceImpl extends SerializableApiResult implements AllianceList.Alliance {

        private long id;
        private String name;
        private String shortName;
        private long executorCorporationId;
        private int memberCount;
        private Date startDate;
	    private SerializableApiListResult<DefaultAllianceList.CorporationImpl> corporations;


        @Override
        public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
            XmlUtility xml = new XmlUtility(xmlResult);
	        
            id = Long.parseLong(xml.getAttribute("allianceID"));
            name = xml.getAttribute("name");
            shortName = xml.getAttribute("shortName");
            executorCorporationId = Long.parseLong(xml.getAttribute("executorCorpID"));
            memberCount = Integer.parseInt(xml.getAttribute("memberCount"));
            startDate = DateUtility.parse(xml.getAttribute("startDate"), xmlApiResult.getTimeDifference(), TimeUnit.MILLISECONDS);

	        corporations = new SerializableApiListResult<DefaultAllianceList.CorporationImpl>(xmlApiResult, xmlResult) {
                @Override
                public Class<? extends CorporationImpl> getRowDefinition() {
                    return DefaultAllianceList.CorporationImpl.class;
                }
            };
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
        public String getShortName() {
            return shortName;
        }

        @Override
        public long getExecutorCorporationId() {
            return executorCorporationId;
        }

        @Override
        public int getMemberCount() {
            return memberCount;
        }

        @Override
        public Date getStartDate() {
            return startDate;
        }

        @Override
        public ApiListResult<? extends AllianceList.Corporation> getCorporations() {
            return corporations;
        }
    }

    public static class CorporationImpl extends SerializableApiResult implements AllianceList.Corporation {

        private long id;
        private Date startDate;

        @Override
        public void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException {
            XmlUtility xml = new XmlUtility(xmlResult);
            
            id = Long.parseLong(xml.getAttribute("corporationID"));
            startDate = DateUtility.parse(xml.getAttribute("startDate"), xmlApiResult.getTimeDifference(), TimeUnit.MILLISECONDS);
        }

        @Override
        public long getId() {
            return id;
        }

        @Override
        public Date getStartDate() {
            return startDate;
        }
    }
}