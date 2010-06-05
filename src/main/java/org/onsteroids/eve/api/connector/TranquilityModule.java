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
