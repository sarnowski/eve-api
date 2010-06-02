/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider;

import com.eveonline.api.ApiKey;
import com.eveonline.api.ApiListResult;
import com.eveonline.api.ApiResult;
import com.google.common.base.Function;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.onsteroids.eve.api.connector.XmlApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
public abstract class AbstractApiListResult<T extends ApiResult> extends ForwardingList<T> implements ApiListResult<T> {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractApiListResult.class);

	private XmlApiResult apiResult;

	private final List<T> results;

	public AbstractApiListResult(List<Node> nodes) {
		results = ImmutableList.copyOf(Lists.transform(nodes, getTransformer()));
	}

	abstract Function<Node, T> getTransformer();

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


	@Override
	protected List<T> delegate() {
		return results;
	}
}
