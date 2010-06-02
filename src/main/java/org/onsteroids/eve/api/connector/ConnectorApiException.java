/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api.connector;

import com.eveonline.api.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class ConnectorApiException extends ApiException {
	private static final Logger LOG = LoggerFactory.getLogger(ConnectorApiException.class);

	public ConnectorApiException(String message) {
		super(message);
	}

	public ConnectorApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectorApiException(Throwable cause) {
		super(cause);
	}
}
