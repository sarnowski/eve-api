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

package org.onsteroids.eve.api.connector.parser;

import com.eveonline.api.ApiKey;
import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.exceptions.ApiResultException;
import org.onsteroids.eve.api.DateUtility;
import org.onsteroids.eve.api.XmlUtility;
import org.onsteroids.eve.api.connector.ApiCoreParser;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.inject.Singleton;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Tobias Sarnowski
 */
@Singleton
final class ApiCoreParserV2 implements ApiCoreParser {
	private static final Logger LOG = LoggerFactory.getLogger(ApiCoreParserV2.class);

	private final static String API_VERSION = "2";


	@Override
	public XmlApiResult call(Document xmlDoc, final String xmlPath, final ApiKey key, final Map<String, String> parameters, final URI serverUri) throws ApiException {
		try {
			final Element root = xmlDoc.getDocumentElement();
			final XmlUtility xml = new XmlUtility(root);

			// eveapi xml?
			if (!"eveapi".equals(root.getNodeName())) {
				throw new ApiProtocolException("unexpected document root name: " + root.getNodeName());
			}

			// correct version?
			final String version = xml.getAttribute("version");
			if (version == null || !API_VERSION.equals(version)) {
				throw new ApiProtocolException("library only compatible with api version '" + API_VERSION + "'; got '" + version + "'");
			} else {
				LOG.trace("Api version {} correct.", API_VERSION);
			}

			// get current server time
			final Date serverCurrentTime;
			String currentTimeText = xml.getContentOf("currentTime");
			serverCurrentTime = DateUtility.parse(currentTimeText);
			LOG.trace("Current server time: {}", serverCurrentTime);

			// get cache times
			final Date serverCachedUntil;
			String currentCachedText = xml.getContentOf("cachedUntil");
			serverCachedUntil = DateUtility.parse(currentCachedText);
			LOG.trace("Cached until: {}", serverCachedUntil);

			final Date now = new Date();
			final long timeDiff = serverCurrentTime.getTime() + now.getTime();
			LOG.trace("Time difference: {} seconds", timeDiff);

			final Date cachedUntil = DateUtility.withTimeDifference(serverCachedUntil, timeDiff, TimeUnit.MILLISECONDS);
			LOG.trace("Locally cached until: {}", cachedUntil);

			// error message?
			if (xml.hasNodeWithName("error")) {
				Node errorNode = xml.getNodeByName("error");
				long errorCode = Long.parseLong(XmlUtility.getAttribute("code", errorNode));
				String errorMessage = errorNode.getTextContent();
				throw new ApiResultException(errorCode, errorMessage);
			}

			// now get the content
			final Node result;
			result = xml.getNodeByName("result");

			LOG.debug("Returning new result from server.");

			// return the results
			return new XmlApiResult() {
                @Override
				public Node getResult() {
					return result;
				}

                @Override
                public long getTimeDifference() {
                    return timeDiff;
                }

                @Override
                public Date getDateCreated() {
					return now;
				}

                @Override
				public Date getCachedUntil() {
					return cachedUntil;
				}

                @Override
				public String getRequestedXmlPath() {
					return xmlPath;
				}

                @Override
				public ApiKey getUsedApiKey() {
					return key;
				}

                @Override
				public Map<String, String> getUsedParameters() {
					return parameters;
				}

                @Override
				public URI getUsedServerUri() {
					return serverUri;
				}
			};
		} catch (Exception e) {
			throw new ApiProtocolException(e);
		}
	}
}
