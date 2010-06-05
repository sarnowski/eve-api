package org.onsteroids.eve.api.cache;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class NoApiCacheModule implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(NoApiCacheModule.class);

	@Override
	public void configure(Binder binder) {
		binder.bind(ApiCache.class).to(NoApiCache.class).in(Singleton.class);
	}
}
