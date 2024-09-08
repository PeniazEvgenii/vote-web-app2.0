package by.it_academy.jd2.service.factory;

import by.it_academy.jd2.service.ServiceGetData;
import by.it_academy.jd2.service.api.IServiceGetData;
import by.it_academy.jd2.storage.factory.VoteStorageFactory;

public class ServiceGetDataFactory {
    private static final IServiceGetData instance = new ServiceGetData(VoteStorageFactory.getInstance());

    private ServiceGetDataFactory() {}

    public static IServiceGetData getInstance() {
        return instance;
    }
}
