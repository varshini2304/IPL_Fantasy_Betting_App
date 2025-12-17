package com.iplfantasy.repository;

import com.iplfantasy.entity.Match;
import com.iplfantasy.entity.MatchStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatchRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Match match) {
        session().persist(match);
    }

    public void update(Match match) {
        session().merge(match);
    }

    public Match findById(Long id) {
        return session().get(Match.class, id);
    }

    public List<Match> findAll() {
        return session()
                .createQuery("FROM Match", Match.class)
                .list();
    }

    public List<Match> findUpcoming() {
        String hql = "FROM Match WHERE matchStatus = :st ORDER BY matchStartTime ASC";
        return session()
                .createQuery(hql, Match.class)
                .setParameter("st", MatchStatus.UPCOMING)
                .list();
    }

    public List<Match> findCurrentMatches() {
        String hql = """
                FROM Match
                WHERE matchStatus IN (:status1, :status2, :status3)
                ORDER BY matchStartTime DESC
                """;
        return session()
                .createQuery(hql, Match.class)
                .setParameter("status1", MatchStatus.UPCOMING)
                .setParameter("status2", MatchStatus.LIVE)
                .setParameter("status3", MatchStatus.COMPLETED)
                .list();
    }

    public List<Match> findCompletedMatchesByTeam(com.iplfantasy.entity.Team team) {
        String hql = """
                FROM Match m
                WHERE m.matchStatus = :status
                AND (m.team1 = :team OR m.team2 = :team)
                """;
        return session()
                .createQuery(hql, Match.class)
                .setParameter("status", MatchStatus.COMPLETED)
                .setParameter("team", team)
                .list();
    }
}
