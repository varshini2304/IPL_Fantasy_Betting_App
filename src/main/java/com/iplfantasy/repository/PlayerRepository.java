package com.iplfantasy.repository;

import com.iplfantasy.entity.Player;
import com.iplfantasy.entity.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Player player) {
        session().persist(player);
    }

    public void update(Player player) {
        session().merge(player);
    }

    public Player findById(Long id) {
        return session().get(Player.class, id);
    }

    public List<Player> findByTeam(Team team) {
        String hql = "FROM Player WHERE team = :t ORDER BY playerName";
        return session()
                .createQuery(hql, Player.class)
                .setParameter("t", team)
                .list();
    }

    public List<Player> findByTeams(List<Team> teams) {
        String hql = "FROM Player WHERE team IN :teams ORDER BY team.teamName, playerName";
        return session()
                .createQuery(hql, Player.class)
                .setParameter("teams", teams)
                .list();
    }
}
