package org.onsteroids.eve.api.cache;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.server.ServerStatus;
import com.eveonline.api.server.ServerStatusApi;
import org.junit.Test;
import org.onsteroids.eve.api.provider.AbstractApiTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author Tobias Sarnowski
 */
public class NokResultCaching extends AbstractApiTest {
    private static final Logger LOG = LoggerFactory.getLogger(NokResultCaching.class);

    @Test
    public void testResult() throws ApiException {
        ServerStatusApi serverStatusApi = getService(ServerStatusApi.class);

        // first time, get a new one
        LOG.info("Retrieving ServerStatus the first time...");
		ServerStatus serverStatus = serverStatusApi.getServerStatus();
        final Date created = serverStatus.getDateCreated();
        final Date cached = serverStatus.getCachedUntil();

        // the second time, we should get the same result
        LOG.info("Retrieving ServerStatus the second time...");
        ServerStatus cachedServerStatus = serverStatusApi.getServerStatus();
        assert serverStatus.equals(cachedServerStatus) : "ServerStatus doesn't equals";
        assert cached.equals(cachedServerStatus.getCachedUntil()) : "ServerStatus hasn't the same cachedUntil time";
        assert created.equals(cachedServerStatus.getDateCreated()) : "ServerStatus hasn't the same created time";
    }

}