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
