/**
 * Copyright 2010 Tobias Sarnowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * (c) 2010 Tobias Sarnowski
 * All rights reserved.
 */
package org.onsteroids.eve.api;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.onsteroids.eve.api.cache.InfinispanApiCacheModule;
import org.onsteroids.eve.api.connector.TranquilityModule;
import org.onsteroids.eve.api.connector.http.PooledHttpApiConnectionModule;
import org.onsteroids.eve.api.connector.parser.ApiCoreParserV2Module;
import org.onsteroids.eve.api.provider.ApiServicesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class DefaultTranquilityModule implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultTranquilityModule.class);

	@Override
	public void configure(Binder binder) {
		// use the official ccp api server for the live servers
		binder.install(new TranquilityModule());

		// thread safe, pooled implementation to make high performance http calls
		binder.install(new PooledHttpApiConnectionModule());

		// parser which can handle API version 2
		binder.install(new ApiCoreParserV2Module());

		// use a cache
		binder.install(new InfinispanApiCacheModule());

		// binds all ApiServices
		binder.install(new ApiServicesModule());
	}
}
