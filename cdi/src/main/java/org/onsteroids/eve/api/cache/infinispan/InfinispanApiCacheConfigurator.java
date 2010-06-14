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

package org.onsteroids.eve.api.cache.infinispan;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;

/**
 * @author Tobias Sarnowski
 */
@Singleton
final class InfinispanApiCacheConfigurator implements Provider<Cache> {
    private static final Logger LOG = LoggerFactory.getLogger(InfinispanApiCacheConfigurator.class);

    private final EmbeddedCacheManager cacheManager;

    @Inject
    public InfinispanApiCacheConfigurator(@Api URL configurationFile) throws IOException {
        LOG.info("Using cache configuration: {}", configurationFile);
        cacheManager = new DefaultCacheManager(configurationFile.openStream());
    }

    @Override
    @Singleton
    public Cache get() {
        return cacheManager.getCache();
    }
}