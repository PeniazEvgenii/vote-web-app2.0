package by.it_academy.jd2.service.api;

import java.util.Map;

public interface IGenreService {
    Long create(String name);
    String get(Long id);
    Map<Long, String> getAll();

    boolean delete(String id);
}
