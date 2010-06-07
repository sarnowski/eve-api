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
package org.onsteroids.eve.api.connector.http;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import org.apache.http.client.HttpClient;
import org.onsteroids.eve.api.connector.ApiConnection;
import org.onsteroids.eve.api.connector.ApiServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class PooledHttpApiConnectionModule implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(PooledHttpApiConnectionModule.class);

	public void configure(Binder binder) {
		binder.bind(ApiConnection.class).to(PooledHttpApiConnection.class).in(Singleton.class);
		binder.bind(HttpClient.class).annotatedWith(ApiServer.class).toProvider(PooledHttpApiConnection.class).in(Singleton.class);
	}

}
