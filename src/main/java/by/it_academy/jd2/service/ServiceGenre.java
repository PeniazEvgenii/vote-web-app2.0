package by.it_academy.jd2.service;

import by.it_academy.jd2.entity.Genre;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.storage.api.IStorage;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceGenre implements IGenreService {
    private final IStorage<Genre> genreStorage;

    public ServiceGenre(IStorage<Genre> genreStorage){
        this.genreStorage = genreStorage;
    }

    @Override
    public Long create(String name) {
        if(name.isBlank()) {
            throw new IllegalArgumentException();
        }
        return genreStorage.create(new Genre(name));
    }

    @Override
    public String get(Long id) {
        Genre genre = genreStorage.get(id);
        return genre.getName();
    }

    @Override
    public Map<Long, String> getAll() {
        Map<Long, Genre> longStringMap = genreStorage.getAll();
        return longStringMap
                .entrySet().stream()
                .collect(Collectors
                        .toMap(Map.Entry::getKey, e -> e.getValue().getName()));
    }

    @Override
    public boolean delete(String deleteGenre) {
            return genreStorage.delete(Long.valueOf(deleteGenre));
    }

}
