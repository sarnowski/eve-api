/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider;

import com.eveonline.api.ApiService;
import com.eveonline.api.DirectorApiKey;
import com.eveonline.api.FullApiKey;
import com.eveonline.api.LimitedApiKey;
import org.onsteroids.eve.api.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Tobias Sarnowski
 */
public abstract class AbstractApiTest {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractApiTest.class);

	private final Api api;

	protected AbstractApiTest() {
		api = Api.createDefaultTranquilityApi();
	}

	public <T extends ApiService> T getService(Class<T> service) {
		return api.get(service);
	}

	public LimitedApiKey getLimitedApiKey() {
		return loadApiKey(new File(System.getProperty("user.home") + File.separatorChar + "eve_limited.properties"));
	}

	public FullApiKey getFullApiKey() {
		return loadApiKey(new File(System.getProperty("user.home") + File.separatorChar + "eve_full.properties"));
	}

	public DirectorApiKey getCeoApiKey() {
		return loadApiKey(new File(System.getProperty("user.home") + File.separatorChar + "eve_ceo.properties"));
	}


	private DirectorApiKey loadApiKey(File file) {
		final Properties properties = new Properties();
		try {
			LOG.trace("Loading API Key informations from {}", file);
			properties.load(new FileInputStream(file));
		} catch (IOException e) {
			throw new IllegalStateException("ApiKey file not found: " + file, e);
		}
		return new DirectorApiKey() {
			@Override
			public int getUserId() {
				return Integer.parseInt(properties.getProperty("userID"));
			}

            @Override
            public String getApiKey() {
                return properties.getProperty("apiKey");
            }
		};
	}
}
