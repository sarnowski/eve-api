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
package org.onsteroids.eve.api.provider;

import com.eveonline.api.ApiKey;
import com.eveonline.api.ApiResult;
import com.eveonline.api.exceptions.ApiException;
import com.google.common.base.Preconditions;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.w3c.dom.Node;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public abstract class SerializableApiResult implements ApiResult, Serializable {

	private Date created;
	private Date cachedUntil;

	private String requestedXmlPath;
	private ApiKey usedApiKey;
	private Map<String,String> usedParameters;
	private URI usedServerUri;

	void processCoreResult(XmlApiResult xmlApiResult, Node node) throws ApiException {
		Preconditions.checkNotNull(xmlApiResult, "ApiResult");
		Preconditions.checkNotNull(node, "Node");

		created = xmlApiResult.getDateCreated();
		cachedUntil = xmlApiResult.getCachedUntil();
		requestedXmlPath = xmlApiResult.getRequestedXmlPath();
		usedApiKey = xmlApiResult.getUsedApiKey();
		usedParameters = xmlApiResult.getUsedParameters();
		usedServerUri = xmlApiResult.getUsedServerUri();

		processResult(xmlApiResult, node);
	}

	/**
	 * Processes the XML given by the api server.
	 *
	 * @param xmlResult the result node of an api response
	 */
	public abstract void processResult(XmlApiResult xmlApiResult, Node xmlResult) throws ApiException;

	@Override
	public Date getDateCreated() {
		return created;
	}

	@Override
	public Date getCachedUntil() {
		return cachedUntil;
	}

	@Override
	public String getRequestedXmlPath() {
		return requestedXmlPath;
	}

	@Override
	public ApiKey getUsedApiKey() {
		return usedApiKey;
	}

	@Override
	public Map<String, String> getUsedParameters() {
		return usedParameters;
	}

	@Override
	public URI getUsedServerUri() {
		return usedServerUri;
	}
}
