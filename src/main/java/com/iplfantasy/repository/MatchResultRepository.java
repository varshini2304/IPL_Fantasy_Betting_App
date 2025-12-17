package com.iplfantasy.repository;

import com.iplfantasy.entity.Match;
import com.iplfantasy.entity.MatchResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MatchResultRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void save(MatchResult result) {
        session().persist(result);
    }

    public MatchResult findByMatch(Match match) {
        String hql = "FROM MatchResult WHERE match = :m";
        return session()
                .createQuery(hql, MatchResult.class)
                .setParameter("m", match)
                .uniqueResult();
    }

    public void update(MatchResult result) {
        session().merge(result);
    }
}
