package org.onsteroids.eve.api.connector.parser;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import org.onsteroids.eve.api.connector.ApiCoreParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class ApiCoreParserV2Module implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(ApiCoreParserV2Module.class);

	@Override
	public void configure(Binder binder) {
		binder.bind(ApiCoreParser.class).to(ApiCoreParserV2.class).in(Singleton.class);
	}
}
