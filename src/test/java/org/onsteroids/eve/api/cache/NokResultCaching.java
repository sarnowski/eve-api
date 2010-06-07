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

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.server.ServerStatus;
import com.eveonline.api.server.ServerStatusApi;
import org.junit.Test;
import org.onsteroids.eve.api.provider.AbstractApiTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author Tobias Sarnowski
 */
public class NokResultCaching extends AbstractApiTest {
    private static final Logger LOG = LoggerFactory.getLogger(NokResultCaching.class);

    @Test
    public void testResult() throws ApiException {
        ServerStatusApi serverStatusApi = getService(ServerStatusApi.class);

        // first time, get a new one
        LOG.info("Retrieving ServerStatus the first time...");
		ServerStatus serverStatus = serverStatusApi.getServerStatus();
        final Date created = serverStatus.getDateCreated();
        final Date cached = serverStatus.getCachedUntil();

        // the second time, we should get the same result
        LOG.info("Retrieving ServerStatus the second time...");
        ServerStatus cachedServerStatus = serverStatusApi.getServerStatus();
        assert serverStatus.equals(cachedServerStatus) : "ServerStatus doesn't equals";
        assert cached.equals(cachedServerStatus.getCachedUntil()) : "ServerStatus hasn't the same cachedUntil time";
        assert created.equals(cachedServerStatus.getDateCreated()) : "ServerStatus hasn't the same created time";
    }

}