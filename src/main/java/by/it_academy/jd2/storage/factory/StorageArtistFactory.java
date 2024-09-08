package by.it_academy.jd2.storage.factory;

import by.it_academy.jd2.entity.Artist;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.storage.db.factory.ArtistStorageDBFactory;
import by.it_academy.jd2.storage.memory.factory.ArtistStorageMemoryFactory;

import java.util.Objects;

public class StorageArtistFactory {
    private static final IStorage<Artist> instance;

    static {
        String envConfig = System.getenv("ARTIST_STORAGE_TYPE");
        if (Objects.equals(envConfig, "MemoryStorage")) {
            instance = ArtistStorageMemoryFactory.getInstance();
        } else {
            instance = ArtistStorageDBFactory.getInstance();
        }
    }

    private StorageArtistFactory() {}

    public static IStorage<Artist> getInstance() {
        return instance;
    }
}
