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

package org.onsteroids.eve.api.connector;

import com.eveonline.api.ApiKey;
import com.eveonline.api.exceptions.ApiException;
import org.w3c.dom.Document;

import java.net.URI;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public interface ApiCoreParser {

	/**
	 * @param xmlDoc the xml response from the server
	 * @param xmlPath the used xmlPath
	 * @param key the used api key
	 * @param parameters the user parameters
	 * @param serverUri the used server URI
	 * @return the parsed xml result
	 * @throws ApiException depending on the xml format and server response
	 */
	XmlApiResult call(Document xmlDoc, String xmlPath, ApiKey key, Map<String, String> parameters, URI serverUri) throws ApiException;

}
