package org.onsteroids.eve.api.cache;

import com.eveonline.api.ApiKey;
import com.eveonline.api.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;

/**
 * @author Tobias Sarnowski
 */
class NoApiCache implements ApiCache {
	private static final Logger LOG = LoggerFactory.getLogger(NoApiCache.class);

	@Override
	public void putApiResult(ApiResult apiResult) {
		// nothing to do here
	}

	@Override
	public ApiResult getApiResult(URI serverUri, String xmlPath, ApiKey key, Map<String, String> parameters) {
		// return null everytime
		return null;
	}
}
