package by.it_academy.jd2.storage.memory.factory;

import by.it_academy.jd2.entity.Genre;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.storage.memory.GenreStorageMemory;

public class GenreStorageMemoryFactory {
    private static final IStorage<Genre> instance = new GenreStorageMemory();

    private GenreStorageMemoryFactory() {}

    public static IStorage<Genre> getInstance() {
        return instance;
    }

}
