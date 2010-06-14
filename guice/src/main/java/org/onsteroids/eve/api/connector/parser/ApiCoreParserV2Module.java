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

package org.onsteroids.eve.api.connector.parser;

import com.google.inject.Binder;
import com.google.inject.Module;
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
		binder.bind(ApiCoreParser.class).to(ApiCoreParserV2.class);
    }
}
