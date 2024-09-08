package by.it_academy.jd2.storage.factory;

import by.it_academy.jd2.entity.VoteEntity;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.storage.db.factory.VoteStorageDBFactory;
import by.it_academy.jd2.storage.memory.factory.VoteStorageMemoryFactory;

import java.util.Objects;

public class VoteStorageFactory {
    private static final IStorage<VoteEntity> instance;

    static {
        String envConfig = System.getenv("VOTE_STORAGE_TYPE");
        if(Objects.equals(envConfig, "MemoryStorage")) {
            instance = VoteStorageMemoryFactory.getInstance();
        } else {
            instance = VoteStorageDBFactory.getInstance();
        }
    }

    private VoteStorageFactory() {}

    public static IStorage<VoteEntity> getInstance() {
        return instance;
    }
}
