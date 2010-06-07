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
package org.onsteroids.eve.api.connector.http;

import com.eveonline.api.ApiKey;
import com.eveonline.api.exceptions.ApiException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.onsteroids.eve.api.InternalApiException;
import org.onsteroids.eve.api.connector.ApiConnection;
import org.onsteroids.eve.api.connector.ApiCoreParser;
import org.onsteroids.eve.api.connector.ApiServer;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
class PooledHttpApiConnection implements ApiConnection, Provider<HttpClient> {
	private static final Logger LOG = LoggerFactory.getLogger(PooledHttpApiConnection.class);

	// xml parser
	private final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

	// the configured server URI
	private final URI serverUri;

	// the parser to use
	private ApiCoreParser apiCoreParser;

	// the http client to use for fetching xml
	private HttpClient httpClient;

	// http configurations
	private int maxConnections = 10;

	@Inject
	public PooledHttpApiConnection(@ApiServer URI serverUri, ApiCoreParser apiCoreParser) {
		this.serverUri = serverUri;
		this.apiCoreParser = apiCoreParser;
	}

	@Inject(optional = true)
	public void setMaxConnections(
			@Named(PooledHttpApiConnectionConfig.MAX_CONNECTIONS) int maxConnections) {
		this.maxConnections = maxConnections;
	}


	@Override
	public HttpClient get() {
		if (httpClient == null) {
			initializeHttpClient();
		}
		return httpClient;
	}

	/**
	 * Initializses and configures the http client connection pool.
	 */
	private void initializeHttpClient() {
		LOG.debug("Configuring the HttpClientPool with a maximum of {} connections", maxConnections);
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, maxConnections);
		ConnPerRouteBean connPerRoute = new ConnPerRouteBean(maxConnections);
		HttpHost serverHost = new HttpHost(serverUri.getHost(), serverUri.getPort());
		connPerRoute.setMaxForRoute(new HttpRoute(serverHost), maxConnections);
		ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
		httpClient = new DefaultHttpClient(cm, params);
	}


	@Override
	public XmlApiResult call(final String xmlPath, final ApiKey key, final Map<String, String> parameters) throws ApiException {
		if (httpClient == null) {
			initializeHttpClient();
		}

		Preconditions.checkNotNull(xmlPath, "XmlPath");
		LOG.debug("Requesting {}...", xmlPath);

		try {
			// build query
			List<NameValuePair> qparams = Lists.newArrayList();
			if (key != null) {
				LOG.trace("Using ApiKey {}", key);
				qparams.add(new BasicNameValuePair("userID", Integer.toString(key.getUserId())));
				qparams.add(new BasicNameValuePair("apiKey", key.getApiKey()));
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

			// process the xml
			return apiCoreParser.call(doc, xmlPath, key, parameters, serverUri);

		} catch (ApiException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalApiException(e);
		}
	}

	@Override
	public URI getServerUri() {
		return serverUri;
	}

	@Override
	public XmlApiResult call(String xmlPath) throws ApiException {
		return call(xmlPath, null, null);
	}

	@Override
	public XmlApiResult call(String xmlPath, Map<String, String> parameters) throws ApiException {
		return call(xmlPath, null, parameters);
	}

	@Override
	public XmlApiResult call(String xmlPath, ApiKey key) throws ApiException {
		return call(xmlPath, key, null);
	}
}
