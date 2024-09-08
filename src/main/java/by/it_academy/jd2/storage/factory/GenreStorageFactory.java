package by.it_academy.jd2.storage.factory;

import by.it_academy.jd2.entity.Genre;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.storage.db.factory.GenreStorageDBFactory;
import by.it_academy.jd2.storage.memory.factory.GenreStorageMemoryFactory;

import java.util.Objects;

public class GenreStorageFactory {
    private static final IStorage<Genre> instance;

    static {
        String envConfig = System.getenv("GENRE_STORAGE_TYPE");
        if(Objects.equals(envConfig, "MemoryStorage")) {
            instance = GenreStorageMemoryFactory.getInstance();
        } else {
            instance = GenreStorageDBFactory.getInstance();
        }
    }

    private GenreStorageFactory() {}

    public static IStorage<Genre> getInstance() {
        return instance;
    }
}
