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
package org.onsteroids.eve.api.connector;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Binds the @ApiServer java.net.URI to the official Tranquility address.
 *
 * @author Tobias Sarnowski
 */
public class TranquilityModule implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(TranquilityModule.class);

	private static final String TRANQUILITY_URL = "http://api.eve-online.com";

	private final URI tranquilityURL;

	public TranquilityModule() {
		try {
			this.tranquilityURL = new URI(TRANQUILITY_URL);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Cannot initialize Tranquility URL: " + TRANQUILITY_URL, e);
		}
	}

	public void configure(Binder binder) {
		binder.bind(URI.class).annotatedWith(ApiServer.class).toInstance(tranquilityURL);
	}
}
