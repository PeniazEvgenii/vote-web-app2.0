package by.it_academy.jd2.storage.db.factory;

import by.it_academy.jd2.entity.Artist;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.storage.connection.factory.ConnectionManagerFactory;
import by.it_academy.jd2.storage.db.ArtistStorageDB;

public class ArtistStorageDBFactory {
    private static final IStorage<Artist> instance = new ArtistStorageDB(ConnectionManagerFactory.getInstance());


    public static IStorage<Artist> getInstance() {
        return instance;
    }
}
