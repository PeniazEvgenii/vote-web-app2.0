package by.it_academy.jd2.storage.db;

import by.it_academy.jd2.entity.Genre;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.storage.connection.api.IConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreStorageDB implements IStorage<Genre> {

    private final IConnectionManager connectionManager;

    private static final String SQL_INSERT_GENRE = "INSERT INTO app.genre (name) VALUES (?)";
    private static final String SQL_GET_GENRE = "SELECT name FROM app.genre WHERE id = ?";
    private static final String SQL_GET_ALL_GENRE = "SELECT id, name FROM app.genre";
    private static final String SQL_DELETE_GENRE = "DELETE FROM app.genre WHERE id = ?";
    private static final String SQL_DELETE_GENRE_IN_CROSS = "DELETE FROM app.cross_vote_genre WHERE genre_id = ?";
    private static final String SQL_GET_GENRE_BY_NAME = "SELECT id, name FROM app.genre WHERE name = ?";

    public GenreStorageDB(IConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Long create(Genre genre) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_GENRE, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, genre.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            Long id = null;
            if (generatedKeys.next()) {
                id = generatedKeys.getLong("id");
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Genre get(Long id) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRE);
        ) {
            Genre genre = null;
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                genre = new Genre(id,
                        resultSet.getString("name"));
            }
            return genre;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении жанра",e);
        }
    }

    @Override
    public Map<Long, Genre> getAll() {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_GENRE);
        ) {
            Map<Long, Genre> result = new HashMap<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                result.put(id, new Genre(id, name));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении жанров",e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = connectionManager.open();
             PreparedStatement prepareStatement = connection.prepareStatement(SQL_DELETE_GENRE);
             PreparedStatement prepareStatementCross = connection.prepareStatement(SQL_DELETE_GENRE_IN_CROSS)
        ) {
            connection.setAutoCommit(false);

            try {
                prepareStatementCross.setLong(1, id);
                prepareStatementCross.executeUpdate();

                prepareStatement.setLong(1, id);
                prepareStatement.executeUpdate();

                connection.commit();
                return true;
            } catch (Exception e) {

                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении из базы данных", e);
        }
    }

    public List<Genre> getByListName(List<String> genres) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRE_BY_NAME);
        ) {
            List<Genre> result = new ArrayList<>();
            for (String genre : genres) {
                preparedStatement.setString(1, genre);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    result.add(new Genre(id, name));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении жанров",e);
        }

    }

}
