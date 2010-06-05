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
