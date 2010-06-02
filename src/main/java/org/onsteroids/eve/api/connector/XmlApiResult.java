/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.connector;

import com.eveonline.api.ApiResult;
import org.w3c.dom.Node;

/**
 * @author Tobias Sarnowski
 */
public interface XmlApiResult extends ApiResult {

	/**
	 * @return returns the &lt;result&gt; node of the api response
	 */
	Node getResult();

}
