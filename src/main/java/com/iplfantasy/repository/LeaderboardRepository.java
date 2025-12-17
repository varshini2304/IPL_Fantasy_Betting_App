package com.iplfantasy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeaderboardRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public List<Object[]> fetchLeaderboard() {

        String hql = """
                SELECT u.userId, u.username, COALESCE(SUM(p.pointsAwarded), 0)
                FROM User u
                LEFT JOIN Prediction p ON u.userId = p.user.userId
                WHERE u.userType != 'ADMIN'
                GROUP BY u.userId, u.username
                ORDER BY COALESCE(SUM(p.pointsAwarded), 0) DESC
                """;

        return session()
                .createQuery(hql, Object[].class)
                .list();
    }
}
