package by.it_academy.jd2.storage.api;

import java.sql.SQLException;
import java.util.Map;

public interface IStorage<T> {

    Long create(T t);

    T get(Long id);

    Map<Long, T> getAll();

    boolean delete(Long id)  throws SQLException;
}
