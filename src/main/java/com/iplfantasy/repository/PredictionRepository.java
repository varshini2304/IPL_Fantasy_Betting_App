package com.iplfantasy.repository;

import com.iplfantasy.entity.Match;
import com.iplfantasy.entity.Prediction;
import com.iplfantasy.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PredictionRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Prediction prediction) {
        session().persist(prediction);
    }

    public void update(Prediction prediction) {
        session().merge(prediction);
    }

    public Prediction findByUserAndMatch(User user, Match match) {
        String hql = """
            FROM Prediction 
            WHERE user = :u AND match = :m
            """;

        return session()
                .createQuery(hql, Prediction.class)
                .setParameter("u", user)
                .setParameter("m", match)
                .uniqueResult();
    }

    public List<Prediction> findByMatch(Match match) {
        String hql = "FROM Prediction WHERE match = :m";
        return session()
                .createQuery(hql, Prediction.class)
                .setParameter("m", match)
                .list();
    }

    public List<Prediction> findByUser(User user) {
        String hql = """
            FROM Prediction
            WHERE user = :u
            ORDER BY predictionTime DESC
            """;

        return session()
                .createQuery(hql, Prediction.class)
                .setParameter("u", user)
                .list();
    }
}
