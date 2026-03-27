package com.kp.game.service.jdbc;

import com.kp.game.entity.Score;
import com.kp.game.service.exceptions.ScoreException;
import com.kp.game.service.interfces.ScoreService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService {
    public static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";//шлях до бази данних
    public static final String USER = "dariia";
    public static final String PASSWORD = "postgres";

    public static final String SELECT =//знайти 10 найкращих за очками
            "SELECT game, player, points, played_on FROM score WHERE game = ? ORDER BY points DESC LIMIT 10";

    public static final String DELETE =//видалити все
            "DELETE FROM score";

    public static final String INSERT =//додати новий рядок у таблицю
            "INSERT INTO score (game, player, points, played_on) VALUES (?, ?, ?, ?)";

    @Override
    public void addScore(Score score) {
        //перший рядок створює зєднання
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             //готує запит це безпечний спосіб підставити дані (замість знаків ?)
             PreparedStatement statement = connection.prepareStatement(INSERT)) {

            statement.setString(1, score.getGame());
            statement.setString(2, score.getPlayer());
            statement.setInt(3, score.getPoints());
            //setTimestamp-це перетворення дати Java у формат, який розуміє база даних.
            statement.setTimestamp(4, new Timestamp(score.getPlayedOn().getTime()));
            //відправляє дані в посигрес скюель
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ScoreException("Problem inserting score", e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT)) {

            statement.setString(1, game);

            try (ResultSet rs = statement.executeQuery()) {
                List<Score> scores = new ArrayList<>();

                while (rs.next()) {
                    scores.add(new Score(
                            rs.getString("game"),
                            rs.getString("player"),
                            rs.getInt("points"),
                            rs.getTimestamp("played_on")
                    ));
                }

                return scores;
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem selecting score", e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(DELETE);

        } catch (SQLException e) {
            throw new ScoreException("Problem deleting score", e);
        }
    }
}