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

package org.onsteroids.eve.api.cache;

import com.eveonline.api.ApiKey;
import com.eveonline.api.ApiResult;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

/**
* @author Tobias Sarnowski
*/
public final class ApiCacheKey implements Serializable {
    private URI serverUri;
    private String xmlPath;
    private long userId;
    private String apiKey;
    private Map<String, String> parameters;

    ApiCacheKey(ApiResult apiResult) {
        serverUri = apiResult.getUsedServerUri();
        xmlPath = apiResult.getRequestedXmlPath();
        if (apiResult.getUsedApiKey() != null) {
            userId = apiResult.getUsedApiKey().getUserId();
            apiKey = apiResult.getUsedApiKey().getApiKey();
        }
        parameters = apiResult.getUsedParameters();
    }

    ApiCacheKey(URI serverUri, String xmlPath, ApiKey apiKey, Map<String, String> parameters) {
        this.serverUri = serverUri;
        this.xmlPath = xmlPath;
        if (apiKey != null) {
            this.userId = apiKey.getUserId();
            this.apiKey = apiKey.getApiKey();
        }
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiCacheKey that = (ApiCacheKey) o;

        if (userId != that.userId) return false;
        if (apiKey != null ? !apiKey.equals(that.apiKey) : that.apiKey != null) return false;
        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) return false;
        if (serverUri != null ? !serverUri.equals(that.serverUri) : that.serverUri != null) return false;
        if (xmlPath != null ? !xmlPath.equals(that.xmlPath) : that.xmlPath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serverUri != null ? serverUri.hashCode() : 0;
        result = 31 * result + (xmlPath != null ? xmlPath.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }
}