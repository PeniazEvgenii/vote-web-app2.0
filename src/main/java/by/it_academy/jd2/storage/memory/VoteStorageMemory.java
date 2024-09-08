package by.it_academy.jd2.storage.memory;

import by.it_academy.jd2.entity.VoteEntity;
import by.it_academy.jd2.storage.api.IStorage;

import java.util.HashMap;
import java.util.Map;

public class VoteStorageMemory implements IStorage<VoteEntity> {

    private final Map<Long, VoteEntity> data = new HashMap<>();

    public VoteStorageMemory() {
    }

    @Override
    public Long create(VoteEntity voteEntity) {
        long id = getNextIdFromMap();
        data.put(id, voteEntity);
        return id;
    }

    @Override
    public VoteEntity get(Long id) {
        return data.get(id);
    }

    @Override
    public Map<Long, VoteEntity> getAll() {
        return data;
    }

    private long getNextIdFromMap() {
        return data.size() + 1;
    }

}
