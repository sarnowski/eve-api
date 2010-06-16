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

package org.onsteroids.eve.api.cache;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.net.URL;

/**
 * @author Tobias Sarnowski
 */
final class DefaultInfinispanApiCacheConfigurator {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultInfinispanApiCacheConfigurator.class);

    private final EmbeddedCacheManager cacheManager;

    public DefaultInfinispanApiCacheConfigurator() {
        final URL usedConfigurationFile;

        URL defaultConfigurationFile = this.getClass().getResource("/eveapi-infinispan-localstorage.xml");
        URL otherConfigurationFile = this.getClass().getResource("/eveapi-infinispan.xml");

        if (otherConfigurationFile != null) {
            usedConfigurationFile = otherConfigurationFile;
        } else if (defaultConfigurationFile != null) {
            usedConfigurationFile = defaultConfigurationFile;
        } else {
            throw new IllegalStateException("Cannot find an infinispan configuration file");
        }

        LOG.info("Using cache configuration: {}", usedConfigurationFile);
        try {
            cacheManager = new DefaultCacheManager(usedConfigurationFile.openStream());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Produces
    @InfinispanApiCache
    public Cache getCache() {
        return cacheManager.getCache();
    }
}