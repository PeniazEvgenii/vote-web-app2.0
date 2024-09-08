package by.it_academy.jd2.storage.memory.factory;

import by.it_academy.jd2.entity.Artist;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.storage.memory.ArtistStorageMemory;

public class ArtistStorageMemoryFactory {
    private static final IStorage<Artist> instance = new ArtistStorageMemory();

    private ArtistStorageMemoryFactory() {
    }

    public static IStorage<Artist> getInstance() {
        return instance;
    }

}
