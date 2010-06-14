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

package org.onsteroids.eve.api.connector.parser;

import com.eveonline.api.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class ApiProtocolException extends ApiException {
	private static final Logger LOG = LoggerFactory.getLogger(ApiProtocolException.class);

	public ApiProtocolException(String message) {
		super(message);
	}

	public ApiProtocolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiProtocolException(Throwable cause) {
		super(cause);
	}
}
