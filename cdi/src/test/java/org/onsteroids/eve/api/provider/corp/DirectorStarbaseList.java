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

package org.onsteroids.eve.api.provider.corp;

import com.eveonline.api.corp.StarbaseList;
import com.eveonline.api.corp.StarbaseListApi;
import com.eveonline.api.exceptions.ApiException;
import com.google.common.base.Preconditions;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onsteroids.eve.api.AbstractArquillianTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Tobias Sarnowski
 */
@RunWith(Arquillian.class)
public class DirectorStarbaseList extends AbstractArquillianTest {
	private static final Logger LOG = LoggerFactory.getLogger(DirectorStarbaseList.class);

	@Inject
	private StarbaseListApi starbaseListApi;

	@Test
	public void retrieveStarbaseList() throws ApiException {
		List<StarbaseList.Starbase> starbases = starbaseListApi.getStarbaseList(getDirectorApiKey(), getCharacterId());
		Preconditions.checkNotNull(starbases, "Starbases");

		for (StarbaseList.Starbase starbase: starbases) {
			LOG.info("Starbase {} Moon {}:  State: {}", new Object[]{
					starbase.getId(),
					starbase.getMoonId(),
					starbase.getState()
			});
		}
	}
}
