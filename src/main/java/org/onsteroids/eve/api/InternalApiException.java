/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api;

import com.eveonline.api.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class InternalApiException extends ApiException {
	private static final Logger LOG = LoggerFactory.getLogger(InternalApiException.class);

	public InternalApiException(String message) {
		super(message);
	}

	public InternalApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalApiException(Throwable cause) {
		super(cause);
	}
}
