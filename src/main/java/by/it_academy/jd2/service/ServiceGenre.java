package by.it_academy.jd2.service;

import by.it_academy.jd2.entity.Genre;
import by.it_academy.jd2.service.api.IGenreService;
import by.it_academy.jd2.storage.api.IStorage;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceGenre implements IGenreService {
    private final IStorage<Genre> genreStorage;

    public ServiceGenre(IStorage<Genre> genreStorage){
        this.genreStorage = genreStorage;
    }

    @Override
    public Long create(String name) {
        Genre genre = new Genre(name);
        return genreStorage.create(genre);
    }

    @Override
    public String get(Long id) {
        Genre genre = genreStorage.get(id);
        return genre.getName();
    }

    @Override
    public Map<Long, String> getAll() {
        Map<Long, Genre> longStringMap = genreStorage.getAll();
        return longStringMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getName()));  //сохраняю старую структуру
    }

    public boolean delete(Long id) {
        try {
            return genreStorage.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении из базы данных" ,e);
        }
    }

}
