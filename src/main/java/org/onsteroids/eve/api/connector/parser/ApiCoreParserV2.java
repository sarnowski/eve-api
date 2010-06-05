package org.onsteroids.eve.api.connector.parser;

import com.eveonline.api.ApiKey;
import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.exceptions.ApiResultException;
import org.onsteroids.eve.api.connector.ApiCoreParser;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.onsteroids.eve.api.provider.XmlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
class ApiCoreParserV2 implements ApiCoreParser {
	private static final Logger LOG = LoggerFactory.getLogger(ApiCoreParserV2.class);

	private final static String API_VERSION = "2";

	private final DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
			serverCurrentTime = dateParser.parse(currentTimeText);
			LOG.trace("Current server time: {}", serverCurrentTime);

			// get cache times
			final Date serverCachedUntil;
			String currentCachedText = xml.getContentOf("cachedUntil");
			serverCachedUntil = dateParser.parse(currentCachedText);
			LOG.trace("Cached until: {}", serverCachedUntil);

			final Date now = new Date();
			long timeDiff = now.getTime() - serverCurrentTime.getTime();
			LOG.trace("Time difference: {} seconds", timeDiff);

			final Date cachedUntil = new Date(serverCachedUntil.getTime() + timeDiff);
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
				public Node getResult() {
					return result;
				}

				public Date getDateCreated() {
					return now;
				}

				public Date getCachedUntil() {
					return cachedUntil;
				}

				public String getRequestedXmlPath() {
					return xmlPath;
				}

				public ApiKey getUsedApiKey() {
					return key;
				}

				public Map<String, String> getUsedParameters() {
					return parameters;
				}

				public URI getUsedServerUri() {
					return serverUri;
				}
			};
		} catch (Exception e) {
			throw new ApiProtocolException(e);
		}
	}
}
