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

import com.eveonline.api.ApiService;
import com.google.common.base.Preconditions;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public final class WeldApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(WeldApi.class);

    private static Weld weld;
    private static WeldContainer weldContainer;

    WeldApi() {

    }

    public static Api createApi() {
        if (weld == null) {
            weld = new Weld();
            weldContainer = weld.initialize();
        }
        return new WeldApi();
    }

    public synchronized static void shutdownApi() {
        Preconditions.checkNotNull(weld, "Weld not running");
        weld.shutdown();
        weld = null;
        weldContainer = null;
    }

    @Override
    public <T extends ApiService> T get(Class<T> apiService) {
        Preconditions.checkNotNull(weld, "Weld not running");
        return weldContainer.instance().select(apiService).get();
    }
}