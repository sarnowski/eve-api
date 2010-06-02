/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api;

import com.google.inject.ConfigurationException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class NokGuice {
	private static final Logger LOG = LoggerFactory.getLogger(NokGuice.class);

	@Test
	public void createApiTest() {
		Api api = Api.createDefaultTranquilityApi();
	}

	@Test(expected = ConfigurationException.class)
	public void createApiTestWithoutModules() {
		Api api = Api.createApi();
	}
}
