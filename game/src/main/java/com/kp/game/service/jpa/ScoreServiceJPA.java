package com.kp.game.service.jpa;

import com.kp.game.entity.Score;
import com.kp.game.service.interfces.ScoreService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

//Це означає, що цей клас є Spring bean, тобто сервісний компонен, спріннг може створити обєкт , підставити залежності
@Service
//Це безпечне виконання дій із базою.Наприклад:пояати операцію , виконати зміни , і якщо все добре зберегти
@Transactional
public class ScoreServiceJPA implements ScoreService {
    //Spring сам дає мені об’єкт, який вже підключений до бази.
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) {
        //зберегти новий обєкт скор в базу данних
        entityManager.persist(score);
    }

    @Override
    public List<Score> getTopScores(String game) {
        return entityManager.createQuery(
                        "SELECT s FROM Score s WHERE s.game = :game ORDER BY s.points DESC",
                        Score.class)
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }


    @Override
    public void reset() {
        entityManager.createQuery("DELETE FROM Score").executeUpdate();
    }
}