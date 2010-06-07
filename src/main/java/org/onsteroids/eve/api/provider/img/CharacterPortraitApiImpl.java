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

package org.onsteroids.eve.api.provider.img;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.img.CharacterPortrait;
import com.eveonline.api.img.CharacterPortraitApi;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.onsteroids.eve.api.InternalApiException;
import org.onsteroids.eve.api.connector.ApiServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author Tobias Sarnowski
 */
public class CharacterPortraitApiImpl implements CharacterPortraitApi {
	private static final Logger LOG = LoggerFactory.getLogger(CharacterPortraitApiImpl.class);

	private static final String FORMAT = "image/jpeg";

	private final Provider<HttpClient> httpClientProvider;
	private final URI serverUri;


	@Inject
	public CharacterPortraitApiImpl(@ApiServer Provider<HttpClient> httpClientProvider) throws URISyntaxException {
		this.httpClientProvider = httpClientProvider;
		serverUri = new URI(CharacterPortraitApi.URL);
	}


	@Override
	public CharacterPortrait getCharacterPortrait(long characterId, PortraitSize size) throws ApiException {
		HttpClient httpClient = httpClientProvider.get();

		List<NameValuePair> qparams = Lists.newArrayList();
		qparams.add(new BasicNameValuePair("c", Long.toString(characterId)));
		qparams.add(new BasicNameValuePair("s", Integer.toString(size.getSize())));

		final URI requestURI;
		try {
			requestURI = URIUtils.createURI(
					serverUri.getScheme(),
					serverUri.getHost(),
					serverUri.getPort(),
					serverUri.getPath(),
					URLEncodedUtils.format(qparams, "UTF-8"),
					null
			);
		} catch (URISyntaxException e) {
			throw new InternalApiException(e);
		}

		LOG.trace("Resulting URI: {}", requestURI);
		final HttpPost postRequest = new HttpPost(requestURI);

		// make the real call
		LOG.trace("Fetching result from {}...", serverUri);
		final HttpResponse response;
		try {
			response = httpClient.execute(postRequest);
		} catch (IOException e) {
			throw new InternalApiException(e);
		}

		if (!FORMAT.equals(response.getEntity().getContentType().getValue())) {
			throw new InternalApiException("Failed to fetch portrait [" + response.getEntity().getContentType().getValue() + "].");
		}

		try {
			return new CharacterPortraitImpl(response);
		} catch (IOException e) {
			throw new InternalApiException(e);
		}
	}
}
