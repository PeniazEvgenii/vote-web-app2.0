package by.it_academy.jd2.storage.connection.factory;

import by.it_academy.jd2.storage.connection.ConnectionManagerManager;
import by.it_academy.jd2.storage.connection.api.IConnectionManager;

public class ConnectionManagerFactory {
    private static final IConnectionManager instance = new ConnectionManagerManager();

    private ConnectionManagerFactory() {}

    public static IConnectionManager getInstance() {
        return instance;
    }
}
