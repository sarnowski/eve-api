/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider;

import com.eveonline.api.ApiKey;
import com.google.common.base.Preconditions;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.net.URI;
import java.util.Date;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public abstract class AbstractApiResult implements XmlApiResult {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractApiResult.class);
	
	private XmlApiResult apiResult;
	private Node result;

	public AbstractApiResult(XmlApiResult apiResult) {
		this(Preconditions.checkNotNull(apiResult, "ApiResult"), apiResult.getResult());
	}

	public AbstractApiResult(XmlApiResult apiResult, Node subNode) {
		this.apiResult = Preconditions.checkNotNull(apiResult, "ApiResult");
		this.result = Preconditions.checkNotNull(subNode, "XML");
	}

	@Override
	public Node getResult() {
		return result;
	}

	public XmlApiResult getXmlApiResult() {
		return apiResult;
	}

	@Override
	public Date getDateCreated() {
		return apiResult.getDateCreated();
	}

	@Override
	public Date getCachedUntil() {
		return apiResult.getCachedUntil();
	}

	@Override
	public String getRequestedXmlPath() {
		return apiResult.getRequestedXmlPath();
	}

	@Override
	public ApiKey getUsedApiKey() {
		return apiResult.getUsedApiKey();
	}

	@Override
	public Map<String, String> getUsedParameters() {
		return apiResult.getUsedParameters();
	}

	@Override
	public URI getUsedServerUri() {
		return apiResult.getUsedServerUri();
	}
}
