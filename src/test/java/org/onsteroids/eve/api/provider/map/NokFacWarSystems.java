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
import com.eveonline.api.map.FacWarSystems;
import com.eveonline.api.map.FacWarSystemsApi;
import org.junit.Test;
import org.onsteroids.eve.api.provider.AbstractApiTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class NokFacWarSystems extends AbstractApiTest {
	private static final Logger LOG = LoggerFactory.getLogger(NokFacWarSystems.class);

	@Test
	public void retrieveJumps() throws ApiException {
		FacWarSystemsApi facWarSystemsApi = getService(FacWarSystemsApi.class);
        FacWarSystems<FacWarSystems.SolarSystem> solarSystems = facWarSystemsApi.getFactionWarfareSystems();

        for (FacWarSystems.SolarSystem solarSystem: solarSystems) {
            LOG.info("SolarSystem: {} [{}]  Faction: {} [{}]  {}", new Object[]{
                    solarSystem.getName(),
                    solarSystem.getId(),
                    solarSystem.getOccupyingFactionName(),
                    solarSystem.getOccupyingFactionId(),
                    solarSystem.isContested() ? "CONTESTED" : "not contested"
            });
        }
	}
}