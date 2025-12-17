package com.iplfantasy.repository;

import com.iplfantasy.entity.PointsHistory;
import com.iplfantasy.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PointsHistoryRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void save(PointsHistory history) {
        session().persist(history);
    }

    public List<PointsHistory> findByUser(User user) {
        String hql = """
            FROM PointsHistory
            WHERE user = :u
            ORDER BY timestamp DESC
            """;

        return session()
                .createQuery(hql, PointsHistory.class)
                .setParameter("u", user)
                .list();
    }
}
