/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.provider;

import com.eveonline.api.ApiListResult;
import com.eveonline.api.ApiResult;
import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.util.List;

/**
 * @author Tobias Sarnowski
 */
public abstract class AbstractApiService {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractApiService.class);

	public <T extends ApiResult> ApiListResult<T> getRowset(Node result, final Function<Node, T> transformer) {
		Node rowset = XmlUtility.getNodeByName("rowset", result);
		List<Node> rows = XmlUtility.getNodesByName("row", rowset);
		return new AbstractApiListResult<T>(rows) {
			@Override
			Function<Node, T> getTransformer() {
				return transformer;
			}
		};
	}
}
