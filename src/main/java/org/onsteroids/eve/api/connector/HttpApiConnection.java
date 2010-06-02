/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.connector;

import com.eveonline.api.ApiKey;
import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.exceptions.ApiResultException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
class HttpApiConnection implements ApiConnection {
	private static final Logger LOG = LoggerFactory.getLogger(HttpApiConnection.class);

	private final static String API_VERSION = "2";

	private final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	private final DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final URI serverUri;
	private final HttpClient httpClient = new DefaultHttpClient();

	HttpApiConnection(URI serverUri) {
		this.serverUri = serverUri;
	}

	public URI getServerUri() {
		return serverUri;
	}


	public XmlApiResult call(String xmlPath) throws ApiException {
		return call(xmlPath, null, null);
	}

	public XmlApiResult call(String xmlPath, Map<String, String> parameters) throws ApiException {
		return call(xmlPath, null, parameters);
	}

	public XmlApiResult call(String xmlPath, ApiKey key) throws ApiException {
		return call(xmlPath, key, null);
	}


	public XmlApiResult call(final String xmlPath, final ApiKey key, final Map<String, String> parameters) throws ApiException {
		Preconditions.checkNotNull(xmlPath, "XmlPath");
		LOG.debug("Calling for {}...", xmlPath);

		try {
			// build query
			List<NameValuePair> qparams = Lists.newArrayList();
			if (key != null) {
				LOG.trace("Using ApiKey {}", key);
				qparams.add(new BasicNameValuePair("userID", Integer.toString(key.getUserId())));
				qparams.add(new BasicNameValuePair("apiKey", key.getKey()));
			}
			if (parameters != null) {
				LOG.trace("Using parameters: {}", parameters);
				for (Map.Entry<String,String> entry: parameters.entrySet()) {
					qparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}

			final URI requestURI = URIUtils.createURI(
					serverUri.getScheme(),
					serverUri.getHost(),
					serverUri.getPort(),
					xmlPath,
					URLEncodedUtils.format(qparams, "UTF-8"),
					null
			);
			LOG.trace("Resulting URI: {}", requestURI);
			final HttpPost postRequest = new HttpPost(requestURI);

			// make the real call
			LOG.trace("Fetching result from {}...", serverUri);
			final HttpResponse response = httpClient.execute(postRequest);
			final InputStream stream = response.getEntity().getContent();

			// parse the xml
			final DocumentBuilder builder = builderFactory.newDocumentBuilder();

			final Document doc = builder.parse(stream);
			final Element root = doc.getDocumentElement();

			// eveapi xml?
			if (!"eveapi".equals(root.getNodeName())) {
				throw new ConnectorApiException("unexpected document root name: " + root.getNodeName());
			}

			// correct version?
			final String version = root.getAttribute("version");
			if (version == null || !API_VERSION.equals(version)) {
				throw new ConnectorApiException("library only compatible with api version '" + API_VERSION + "'; got '" + version + "'");
			} else {
				LOG.trace("Api version {} correct.", API_VERSION);
			}

			// get current server time
			final Date serverCurrentTime;
			NodeList currentTimeList = root.getElementsByTagName("currentTime");
			if (currentTimeList.getLength() != 1) {
				throw new ApiException("unique currentTime expected");
			}
			Node currentTimeNode = currentTimeList.item(0);
			String currentTimeText = currentTimeNode.getTextContent();
			serverCurrentTime = dateParser.parse(currentTimeText);
			LOG.trace("Current server time: {}", serverCurrentTime);

			// get cache times
			final Date serverCachedUntil;
			NodeList currentCachedList = root.getElementsByTagName("cachedUntil");
			if (currentTimeList.getLength() != 1) {
				throw new ApiException("unique cachedUntil expected");
			}
			Node currentCachedNode = currentCachedList.item(0);
			String currentCachedText = currentCachedNode.getTextContent();
			serverCachedUntil = dateParser.parse(currentCachedText);
			LOG.trace("Cached until: {}", serverCachedUntil);

			final Date now = new Date();
			long timeDiff = now.getTime() - serverCurrentTime.getTime();
			LOG.trace("Time difference: {} seconds", timeDiff);
			final Date cachedUntil = new Date(serverCachedUntil.getTime() + timeDiff);

			// error message?
			NodeList errorNodeList = root.getElementsByTagName("error");
			if (errorNodeList.getLength() != 0) {
				Node errorNode = errorNodeList.item(0);
				int errorCode = Integer.parseInt(errorNode.getAttributes().getNamedItem("code").getTextContent());
				String errorMessage = errorNode.getTextContent();
				throw new ApiResultException(errorCode, errorMessage);
			}

			// now get the content
			final Node result;
			NodeList resultNodeList = root.getElementsByTagName("result");
			if (resultNodeList.getLength() != 1) {
				throw new ConnectorApiException("unique result expected");
			}
			result = resultNodeList.item(0);

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
					return requestURI;
				}
			};
			
		} catch (Exception e) {
			if (e instanceof ApiException) {
				throw (ApiException)e;
			} else {
				throw new ConnectorApiException(e);
			}
		}
	}
}
