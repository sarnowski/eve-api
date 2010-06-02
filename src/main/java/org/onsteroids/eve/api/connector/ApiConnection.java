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

	URI getServerUri();


	XmlApiResult call(String xmlPath) throws ApiException;

	XmlApiResult call(String xmlPath, Map<String,String> parameters) throws ApiException;

	XmlApiResult call(String xmlPath, ApiKey key) throws ApiException;

	XmlApiResult call(String xmlPath, ApiKey key, Map<String,String> parameters) throws ApiException;

}
