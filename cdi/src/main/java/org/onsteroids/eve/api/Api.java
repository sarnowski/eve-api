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

package org.onsteroids.eve.api;

import com.eveonline.api.ApiService;

/**
 * @author Tobias Sarnowski
 */
public interface Api {

    /**
	 * @param apiService the API service type to retrieve
	 * @param <T> the service definition
	 * @return the ready-to-use API service instance
	 */
	<T extends ApiService> T get(Class<T> apiService);

}
