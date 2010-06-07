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

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * @author Tobias Sarnowski
 */
public final class InfinispanApiCacheConfiguratorModule implements Module {
    private static final Logger LOG = LoggerFactory.getLogger(InfinispanApiCacheConfiguratorModule.class);

    private final URL configurationFile;

    public InfinispanApiCacheConfiguratorModule() {
        configurationFile = null;
    }

    public InfinispanApiCacheConfiguratorModule(URL configurationFile) {
        this.configurationFile = configurationFile;
    }


    @Override
    public void configure(Binder binder) {
        if (configurationFile != null) {
            binder.bind(URL.class).annotatedWith(Api.class).toInstance(configurationFile);
        }
        binder.bind(Cache.class).annotatedWith(Api.class).toProvider(InfinispanApiCacheConfigurator.class).in(Singleton.class);
    }
}