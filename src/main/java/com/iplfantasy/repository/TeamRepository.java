package com.iplfantasy.repository;

import com.iplfantasy.entity.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Team team) {
        session().persist(team);
    }

    public void update(Team team) {
        session().merge(team);
    }

    public Team findById(Long id) {
        return session().get(Team.class, id);
    }

    public Team findByName(String name) {
        String hql = "FROM Team WHERE teamName = :n";
        return session()
                .createQuery(hql, Team.class)
                .setParameter("n", name)
                .uniqueResult();
    }

    public List<Team> findAll() {
        return session()
                .createQuery("FROM Team ORDER BY teamName", Team.class)
                .list();
    }
}
