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
public class NokJumps extends AbstractArquillianTest {
	private static final Logger LOG = LoggerFactory.getLogger(NokJumps.class);

    @Inject
    private JumpsApi jumpsApi;

	@Test
	public void retrieveJumps() throws ApiException {
		Jumps<Jumps.SolarSystem> jumps = jumpsApi.getJumps();

		LOG.info("DataTime: {}", jumps.getDataTime());

        for (Jumps.SolarSystem solarSystem: jumps) {
            LOG.info("SolarSystem: {}  Jumps: {}", solarSystem.getId(), solarSystem.getShipJumps());
        }
	}
}