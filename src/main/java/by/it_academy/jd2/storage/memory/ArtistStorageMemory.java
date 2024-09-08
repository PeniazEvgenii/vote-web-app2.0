package by.it_academy.jd2.storage.memory;

import by.it_academy.jd2.entity.Artist;
import by.it_academy.jd2.entity.econst.ESingers;
import by.it_academy.jd2.storage.api.IStorage;

import java.util.HashMap;
import java.util.Map;

public class ArtistStorageMemory implements IStorage<Artist> {

    private final Map<Long, Artist> data = new HashMap<>();

    public ArtistStorageMemory() {
        for (ESingers value : ESingers.values()) {
            long id = getNextIdFromMap();
            data.put(id, new Artist(id, value.name()));
        }
    }

    @Override
    public Long create(Artist artist) {
        long id = getNextIdFromMap();
        data.put(id, artist);
        return id;
    }

    @Override
    public Artist get(Long id) {
        return data.get(id);
    }

    @Override
    public Map<Long, Artist> getAll() {
        return this.data;
    }

    private long getNextIdFromMap() {
        return data.size() + 1;
    }
}
