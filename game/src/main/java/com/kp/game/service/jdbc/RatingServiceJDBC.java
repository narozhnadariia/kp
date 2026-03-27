package com.kp.game.service.jdbc;

import com.kp.game.entity.Rating;
import com.kp.game.service.exceptions.RatingException;
import com.kp.game.service.interfces.RatingService;

import java.sql.*;

public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    public static final String USER = "dariia";
    public static final String PASSWORD = "postgres";

    public static final String SELECT_RATING =
            "SELECT rating FROM rating WHERE game = ? AND player = ?";

    public static final String INSERT =
            "INSERT INTO rating (game, player, rating, rated_on) VALUES (?, ?, ?, ?)";

    public static final String UPDATE =
            "UPDATE rating SET rating = ?, rated_on = ? WHERE game = ? AND player = ?";

    public static final String SELECT_AVERAGE =
            "SELECT AVG(rating) AS avg_rating FROM rating WHERE game = ?";

    public static final String DELETE =
            "DELETE FROM rating";

    @Override
    public void setRating(Rating rating) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(SELECT_RATING)) {

            selectStatement.setString(1, rating.getGame());
            selectStatement.setString(2, rating.getPlayer());

            try (ResultSet rs = selectStatement.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement updateStatement = connection.prepareStatement(UPDATE)) {
                        updateStatement.setInt(1, rating.getRating());
                        updateStatement.setTimestamp(2, new Timestamp(rating.getRatedOn().getTime()));
                        updateStatement.setString(3, rating.getGame());
                        updateStatement.setString(4, rating.getPlayer());
                        updateStatement.executeUpdate();
                    }
                } else {
                    try (PreparedStatement insertStatement = connection.prepareStatement(INSERT)) {
                        insertStatement.setString(1, rating.getGame());
                        insertStatement.setString(2, rating.getPlayer());
                        insertStatement.setInt(3, rating.getRating());
                        insertStatement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem setting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_AVERAGE)) {

            statement.setString(1, game);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("avg_rating");
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem getting average rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_RATING)) {

            statement.setString(1, game);
            statement.setString(2, player);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("rating");
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem getting rating", e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(DELETE);

        } catch (SQLException e) {
            throw new RatingException("Problem deleting ratings", e);
        }
    }
}