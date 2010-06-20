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

package org.onsteroids.eve.api;

import com.eveonline.api.DirectorApiKey;
import com.eveonline.api.FullApiKey;
import com.eveonline.api.LimitedApiKey;
import org.jboss.arquillian.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Tobias Sarnowski
 */
public abstract class AbstractArquillianTest {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractArquillianTest.class);

	private Properties properties;


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create("eve-api-cdi.jar", JavaArchive.class)
                .addPackages(true, InternalApiException.class.getPackage());
    }


    public LimitedApiKey getLimitedApiKey() {
	    require("eve_limited.properties");

		return new LimitedApiKey() {
			@Override
			public long getUserId() {
				return Long.parseLong(properties.getProperty("userID"));
			}
            @Override
            public String getApiKey() {
                return properties.getProperty("apiKey");
            }
		};
	}

	public FullApiKey getFullApiKey() {
		require("eve_full.properties");

		return new FullApiKey() {
			@Override
			public long getUserId() {
				return Long.parseLong(properties.getProperty("userID"));
			}
            @Override
            public String getApiKey() {
                return properties.getProperty("apiKey");
            }
		};
	}

	public DirectorApiKey getDirectorApiKey() {
		require("eve_director.properties");

		return new DirectorApiKey() {
			@Override
			public long getUserId() {
				return Long.parseLong(properties.getProperty("userID"));
			}
            @Override
            public String getApiKey() {
                return properties.getProperty("apiKey");
            }
		};
	}


	public long getCharacterId() {
		return Long.parseLong((String) properties.get("characterID"));
	}


	private void require(String fileName) {
		if (properties == null) {
			File file = new File(System.getProperty("user.home") + File.separatorChar + fileName);

			properties = new Properties();
			LOG.trace("Loading test configuration from {}", file);
			try {
				properties.load(new FileInputStream(file));
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}