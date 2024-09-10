package by.it_academy.jd2.storage.db;

import by.it_academy.jd2.entity.Artist;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ArtistStorageDB implements IStorage<Artist> {

    private static final String SQL_INSERT_ARTIST = "INSERT INTO app.artist (name) VALUES (?) returning id";
    private static final String SQL_GET_ARTIST = "SELECT name FROM app.artist WHERE id = ?";
    private static final String SQL_GET_ALL_ARTIST = "SELECT id, name FROM app.artist";

    private static final String SQL_DELETE_ARTIST = "DELETE FROM app.artist WHERE id = ?";
    private static final String SQL_UPDATE_ARTIST_IN_VOTE = "UPDATE app.vote SET artist_id = null WHERE artist_id = ?";


    public ArtistStorageDB() {
    }

    @Override
    public Long create(Artist artist) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ARTIST)) {
            preparedStatement.setString(1, artist.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            Long id = null;
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Artist get(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ARTIST)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Artist artist = null;
            if (resultSet.next()) {
                artist = new Artist(
                        id,
                        resultSet.getString("name"));
            }
            return artist;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Artist> getAll() {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_ARTIST);
        ) {
            Map<Long, Artist> result = new HashMap<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                result.put(id, new Artist(id, name));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement prepareStatementVote = connection.prepareStatement(SQL_UPDATE_ARTIST_IN_VOTE);
             PreparedStatement prepareStatement = connection.prepareStatement(SQL_DELETE_ARTIST)) {

            connection.setAutoCommit(false);

            try {
                prepareStatementVote.setLong(1, id);
                prepareStatementVote.executeUpdate();

                prepareStatement.setLong(1, id);
                prepareStatement.executeUpdate();

                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            throw new SQLException("Ошибка при удалении из базы данных", e);
        }
    }

}
