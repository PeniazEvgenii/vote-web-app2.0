package by.it_academy.jd2.storage.db.factory;

import by.it_academy.jd2.entity.VoteEntity;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.storage.db.VoteStorageDB;

public class VoteStorageDBFactory {
    private static final IStorage<VoteEntity> instance = new VoteStorageDB();

    private VoteStorageDBFactory() {}

    public static IStorage<VoteEntity> getInstance() {
        return instance;
    }

}
