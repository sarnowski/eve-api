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
package org.onsteroids.eve.api.connector;

import com.eveonline.api.ApiKey;
import com.eveonline.api.exceptions.ApiException;

import java.net.URI;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public interface ApiConnection {

	/**
	 * @return the used server URI
	 */
	URI getServerUri();


	/**
	 * @param xmlPath e.g. /server/ServerStatus.xml.aspx
	 * @return the returned response from the server
	 * @throws ApiException
	 */
	XmlApiResult call(String xmlPath) throws ApiException;

	/**
	 * @param xmlPath e.g. /server/ServerStatus.xml.aspx
	 * @param parameters additional parameters to provide, e.g. characterID
	 * @return the returned response from the server
	 * @throws ApiException
	 */
	XmlApiResult call(String xmlPath, Map<String,String> parameters) throws ApiException;

	/**
	 * @param xmlPath e.g. /server/ServerStatus.xml.aspx
	 * @param key the API key to authenticate against
	 * @return the returned response from the server
	 * @throws ApiException
	 */
	XmlApiResult call(String xmlPath, ApiKey key) throws ApiException;

	/**
	 * @param xmlPath e.g. /server/ServerStatus.xml.aspx
	 * @param key the API key to authenticate against
	 * @param parameters additional parameters to provide, e.g. characterID
	 * @return the returned response from the server
	 * @throws ApiException
	 */
	XmlApiResult call(String xmlPath, ApiKey key, Map<String,String> parameters) throws ApiException;

}
