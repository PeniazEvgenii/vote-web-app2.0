package by.it_academy.jd2.storage.memory;

import by.it_academy.jd2.entity.Genre;
import by.it_academy.jd2.entity.econst.EJanres;
import by.it_academy.jd2.storage.api.IStorage;

import java.util.HashMap;
import java.util.Map;

public class GenreStorageMemory implements IStorage<Genre> {
    private final Map<Long, Genre> data = new HashMap<>();

    public GenreStorageMemory() {
        for (EJanres value : EJanres.values()) {
            long id = getNextIdFromMap();
            data.put(id, new Genre(id, value.name()));
        }
    }

    @Override
    public Long create(Genre genre) {
        long id = getNextIdFromMap();
        data.put(id, genre);
        return id;
    }

    private long getNextIdFromMap() {
        return data.size() + 1;
    }

    @Override
    public Genre get(Long id) {
        return this.data.get(id);
    }

    @Override
    public Map<Long, Genre> getAll() {
        return this.data;
    }
}
