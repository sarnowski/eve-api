/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.connector;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * @author Tobias Sarnowski
 */
public class ApiConnectionModule implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(ApiConnectionModule.class);

	public void configure(Binder binder) {
	}

	@Provides
	ApiConnection getApiConnection(@ApiServer URI serverUri) {
		return new HttpApiConnection(serverUri);
	}
}
