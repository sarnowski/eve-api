package org.onsteroids.eve.api;

import com.eveonline.api.exceptions.ApiException;
import com.eveonline.api.server.ServerStatus;
import com.eveonline.api.server.ServerStatusApi;

/**
 * @author Tobias Sarnowski
 */
public class ServerStatusApp {

    public static void main(String[] args) {
        Api api = WeldApi.createApi();
        ServerStatusApi serverStatusApi = api.get(ServerStatusApi.class);

        ServerStatus serverStatus = null;
        try {
            serverStatus = serverStatusApi.getServerStatus();
        } catch (ApiException e) {
            e.printStackTrace();
            System.exit(2);
        }

        if (serverStatus.isServerOpen()) {
            System.out.println(String.format("%s players online", serverStatus.getOnlinePlayers()));
            System.exit(0);

        } else {
            System.out.println("Server is not open.");
            System.exit(1);
        }
    }

}