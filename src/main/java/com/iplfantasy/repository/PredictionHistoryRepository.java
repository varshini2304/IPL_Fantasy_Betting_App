package com.iplfantasy.repository;

import com.iplfantasy.entity.Prediction;
import com.iplfantasy.entity.PredictionHistory;
import com.iplfantasy.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PredictionHistoryRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void save(PredictionHistory history) {
        session().persist(history);
    }

    public List<PredictionHistory> findByPrediction(Prediction prediction) {
        String hql = """
                FROM PredictionHistory
                WHERE prediction = :p
                ORDER BY changeTime DESC
                """;

        return session()
                .createQuery(hql, PredictionHistory.class)
                .setParameter("p", prediction)
                .list();
    }

    public List<PredictionHistory> findByUser(User user) {
        String hql = """
                FROM PredictionHistory
                WHERE user = :u
                ORDER BY changeTime DESC
                """;

        return session()
                .createQuery(hql, PredictionHistory.class)
                .setParameter("u", user)
                .list();
    }
}
