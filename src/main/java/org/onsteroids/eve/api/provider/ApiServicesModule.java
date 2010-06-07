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
package org.onsteroids.eve.api.provider;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.onsteroids.eve.api.provider.account.AccountServicesModule;
import org.onsteroids.eve.api.provider.eve.EveServicesModule;
import org.onsteroids.eve.api.provider.img.ImgServicesModule;
import org.onsteroids.eve.api.provider.map.MapServicesModule;
import org.onsteroids.eve.api.provider.server.ServerServicesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tobias Sarnowski
 */
public class ApiServicesModule implements Module {
	private static final Logger LOG = LoggerFactory.getLogger(ApiServicesModule.class);

	public void configure(Binder binder) {
		binder.install(new AccountServicesModule());
        binder.install(new EveServicesModule());
		binder.install(new ImgServicesModule());
        binder.install(new MapServicesModule());
		binder.install(new ServerServicesModule());
	}
}
