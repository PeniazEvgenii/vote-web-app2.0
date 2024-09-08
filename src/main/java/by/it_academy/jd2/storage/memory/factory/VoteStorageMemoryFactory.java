package by.it_academy.jd2.storage.memory.factory;

import by.it_academy.jd2.entity.VoteEntity;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.storage.memory.VoteStorageMemory;

public class VoteStorageMemoryFactory {
    private static final IStorage<VoteEntity> instance = new VoteStorageMemory();

    private VoteStorageMemoryFactory() {}

    public static IStorage<VoteEntity> getInstance() {
        return instance;
    }

}
