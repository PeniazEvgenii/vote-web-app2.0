package by.it_academy.jd2.storage.db;

import by.it_academy.jd2.entity.VoteEntity;
import by.it_academy.jd2.storage.api.IStorage;
import by.it_academy.jd2.storage.connection.api.IConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteStorageDB implements IStorage<VoteEntity> {
    private final IConnectionManager connectionManager;

    private static final String INSERT_VOTE_SQL = "INSERT INTO app.vote (artist_id, about, create_at) VALUES (?, ?, ?) returning id";
    private static final String INSERT_VOTE_GENRE_SQL = "INSERT INTO app.cross_vote_genre (vote_id, genre_id) VALUES (?, ?)";
    private static final String SELECT_VOTE_SQL = "SELECT vote.id, about, artist_id, create_at FROM app.vote";
    private static final String SELECT_VOTE_BY_ID_SQL = "SELECT id, about, artist_id, create_at FROM app.vote WHERE id = ?";
    private static final String SELECT_GENRE_BY_VOTE_ID = "SELECT genre_id FROM app.cross_vote_genre WHERE vote_id = ?";
    private static final String SQL_DELETE_VOTE = "DELETE FROM app.vote WHERE id = ?";
    private static final String SQL_DELETE_VOTE_IN_CROSS = "DELETE FROM app.cross_vote_genre WHERE vote_id = ?";

    public VoteStorageDB(IConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Long create(VoteEntity voteEntity) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VOTE_SQL);
             PreparedStatement preparedStatementCross = connection.prepareStatement(INSERT_VOTE_GENRE_SQL)) {

            connection.setAutoCommit(false);
            Long vote_id = null;
            try {
                preparedStatement.setLong(1, voteEntity.getArtistId());
                preparedStatement.setString(2, voteEntity.getInfo());
                preparedStatement.setObject(3, voteEntity.getCreateAt());

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    vote_id = resultSet.getLong("id");
                }

                List<Long> genresId = voteEntity.getGenresId();
                for (Long genreId : genresId) {
                    preparedStatementCross.setLong(1, vote_id);
                    preparedStatementCross.setLong(2, genreId);
                    preparedStatementCross.executeUpdate();
                }
                connection.commit();
                return vote_id;
            } catch (Exception e) {
                connection.rollback();
            }

            return vote_id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VoteEntity get(Long id) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_VOTE_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            VoteEntity voteEntity = null;
            if (resultSet.next()) {
                voteEntity = buildVoteEntity(resultSet);
            }

            List<Long> genresByVoice = getGenresByVote(voteEntity.getId(), connection);
            ;
            voteEntity.setGenresId(genresByVoice);

            return voteEntity;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении голоса", e);
        }
    }

    public List<Long> getGenresByVote(Long voteId) {
        try (Connection connection = connectionManager.open()) {
            return getGenresByVote(voteId, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Long> getGenresByVote(Long voteId, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GENRE_BY_VOTE_ID)) {
            preparedStatement.setLong(1, voteId);
            ResultSet resultSet2 = preparedStatement.executeQuery();
            return getListGenres(resultSet2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, VoteEntity> getAll() {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_VOTE_SQL)
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Long, VoteEntity> mapVotes = new HashMap<>();
            while (resultSet.next()) {
                VoteEntity voteEntity = buildVoteEntity(resultSet);
                Long id = voteEntity.getId();
                List<Long> genresByVoice = getGenresByVote(id, connection);
                voteEntity.setGenresId(genresByVoice);

                mapVotes.put(id, voteEntity);
            }

            return mapVotes;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении голосов", e);
        }
    }

    private List<Long> getListGenres(ResultSet resultSet) throws SQLException {
        List<Long> genreId = new ArrayList<>();
        while (resultSet.next()) {
            genreId.add(resultSet.getLong("genre_id"));
        }
        return genreId;
    }

    private VoteEntity buildVoteEntity(ResultSet resultSet) throws SQLException {
        return VoteEntity.builder()
                .setId(resultSet.getLong("id"))
                .setArtist(resultSet.getLong("artist_id"))
                .setInfo(resultSet.getString("about"))
                .setCreateAt(resultSet.getObject("create_at", OffsetDateTime.class))
                .build();
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = connectionManager.open();
             PreparedStatement prepareStatement = connection.prepareStatement(SQL_DELETE_VOTE);
             PreparedStatement prepareStatementCross = connection.prepareStatement(SQL_DELETE_VOTE_IN_CROSS)
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
            throw new RuntimeException("Ошибка при удалении голоса", e);
        }
    }

}
