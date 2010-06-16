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

/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider.server;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.server.ServerStatus;
import com.eveonline.api.server.ServerStatusApi;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onsteroids.eve.api.AbstractArquillianTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * @author Tobias Sarnowski
 */
@RunWith(Arquillian.class)
public class NokServerStatus extends AbstractArquillianTest {
	private static final Logger LOG = LoggerFactory.getLogger(NokServerStatus.class);

    @Inject
    private ServerStatusApi serverStatusApi;

	@Test
	public void retrieveServerStatus(ServerStatusApi serverStatusApi) throws ApiException {
		ServerStatus serverStatus = serverStatusApi.getServerStatus();

		if (serverStatus.isServerOpen()) {
			LOG.info("Tranquility is running with {} players!", serverStatus.getOnlinePlayers());
		} else {
			LOG.info("Tranquility is not running!");
		}
	}
}