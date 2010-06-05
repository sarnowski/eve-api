package org.onsteroids.eve.api.connector.parser;

import com.eveonline.api.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class ApiProtocolException extends ApiException {
	private static final Logger LOG = LoggerFactory.getLogger(ApiProtocolException.class);

	public ApiProtocolException(String message) {
		super(message);
	}

	public ApiProtocolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiProtocolException(Throwable cause) {
		super(cause);
	}
}
