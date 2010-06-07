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

package org.onsteroids.eve.api.provider.img;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.img.CharacterPortrait;
import com.eveonline.api.img.CharacterPortraitApi;
import org.junit.Test;
import org.onsteroids.eve.api.provider.AbstractApiTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Tobias Sarnowski
 */
public class NokCharacterPortrait extends AbstractApiTest {
	private static final Logger LOG = LoggerFactory.getLogger(NokCharacterPortrait.class);

	private final long CHARACTER_ID = 130092986;
	private final CharacterPortraitApi.PortraitSize PORTRAIT_SIZE = CharacterPortraitApi.PortraitSize.SIZE_64;
	
	private final int EXPECTED_SIZE = 1786;

	@Test
	public void retrieveCharacterPortrait() throws ApiException, IOException {
		CharacterPortraitApi characterPortraitApi = getService(CharacterPortraitApi.class);

		CharacterPortrait portait = characterPortraitApi.getCharacterPortrait(CHARACTER_ID, PORTRAIT_SIZE);
		LOG.info("Got portrait of type {} with {} bytes.", portait.getContentType(), portait.getContentLength());

		assert portait.getContentLength() == EXPECTED_SIZE : "Not the correct image size";
	}
}
