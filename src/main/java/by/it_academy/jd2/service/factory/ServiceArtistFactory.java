package by.it_academy.jd2.service.factory;

import by.it_academy.jd2.service.ServiceArtist;
import by.it_academy.jd2.service.api.IArtistService;
import by.it_academy.jd2.storage.factory.StorageArtistFactory;

public class ServiceArtistFactory {
    private static final IArtistService instance = new ServiceArtist(StorageArtistFactory.getInstance());


    private ServiceArtistFactory(){}

    public static IArtistService getInstance() {
        return instance;
    }
}
