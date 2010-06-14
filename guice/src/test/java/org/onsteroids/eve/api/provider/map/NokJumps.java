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
package org.onsteroids.eve.api.provider.map;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.map.Jumps;
import com.eveonline.api.map.JumpsApi;
import org.junit.Test;
import org.onsteroids.eve.api.provider.AbstractApiTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class NokJumps extends AbstractApiTest {
	private static final Logger LOG = LoggerFactory.getLogger(NokJumps.class);

	@Test
	public void retrieveJumps() throws ApiException {
		JumpsApi jumpsApi = getService(JumpsApi.class);
		Jumps<Jumps.SolarSystem> jumps = jumpsApi.getJumps();

		LOG.info("DataTime: {}", jumps.getDataTime());
        
        for (Jumps.SolarSystem solarSystem: jumps) {
            LOG.info("SolarSystem: {}  Jumps: {}", solarSystem.getId(), solarSystem.getShipJumps());
        }
	}
}