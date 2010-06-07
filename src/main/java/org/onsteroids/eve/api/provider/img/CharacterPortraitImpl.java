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

import com.eveonline.api.img.CharacterPortrait;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Tobias Sarnowski
 */
public class CharacterPortraitImpl implements CharacterPortrait {
	private static final Logger LOG = LoggerFactory.getLogger(CharacterPortraitImpl.class);

	private final InputStream inputStream;
	private final long contentLength;
	private final String contentType;

	public CharacterPortraitImpl(HttpResponse response) throws IOException {
		inputStream = response.getEntity().getContent();
		contentLength = response.getEntity().getContentLength();
		contentType = response.getEntity().getContentType().getValue();
	}

	@Override
	public InputStream getContent() {
		return inputStream;
	}

	@Override
	public long getContentLength() {
		return contentLength;
	}

	@Override
	public String getContentType() {
		return contentType;
	}
}
