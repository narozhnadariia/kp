package com.kp.game.service.jpa;

import com.kp.game.entity.Rating;
import com.kp.game.service.interfces.RatingService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        //1 частина ,воно дивиться чи рейтинг вже дбув записаний по назві грі і імені гравця , якщо є оновлює старий на нові значення
        List<Rating> ratings = entityManager.createQuery(
                        "SELECT r FROM Rating r WHERE r.game = :game AND r.player = :player",
                        Rating.class)
                .setParameter("game", rating.getGame())
                .setParameter("player", rating.getPlayer())
                .getResultList();
//2частина , якщо рейтингу ще немає, якщо правда створює новий рядок у таблиці рейтинг
        if (ratings.isEmpty()) {
            entityManager.persist(rating);
        } else {
            Rating existingRating = ratings.get(0);
            //оновлюємо число рейтингу
            existingRating.setRating(rating.getRating());
            //оновлюємо дату рейтингу
            existingRating.setRatedOn(rating.getRatedOn());
            entityManager.merge(existingRating);
        }
    }

    @Override
    public int getAverageRating(String game) {
        Double average = entityManager.createQuery(
                        "SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game",
                        Double.class)
                .setParameter("game", game)
                .getSingleResult();

        return average == null ? 0 : (int) Math.round(average);
    }
    //Повертає рейтинг, який конкретний гравець поставив конкретній грі.
    @Override
    public int getRating(String game, String player) {
        List<Rating> ratings = entityManager.createQuery(
                        "SELECT r FROM Rating r WHERE r.game = :game AND r.player = :player",
                        Rating.class)
                .setParameter("game", game)
                .setParameter("player", player)
                .getResultList();

        return ratings.isEmpty() ? 0 : ratings.get(0).getRating();
    }

    @Override
    public void reset() {
        entityManager.createQuery("DELETE FROM Rating").executeUpdate();
    }
}