package com.iplfantasy.repository;

import com.iplfantasy.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void save(User user) {
        session().persist(user);
    }

    public void update(User user) {
        session().merge(user);
    }

    public User findById(Long id) {
        return session().get(User.class, id);
    }

    public User findByUsername(String username) {
        String hql = "FROM User WHERE username = :un";
        return session()
                .createQuery(hql, User.class)
                .setParameter("un", username)
                .uniqueResult();
    }

    public User findByEmail(String email) {
        String hql = "FROM User WHERE email = :em";
        return session()
                .createQuery(hql, User.class)
                .setParameter("em", email)
                .uniqueResult();
    }

    public User findAdmin() {
        String hql = "FROM User WHERE userType = 'ADMIN'";
        return session()
                .createQuery(hql, User.class)
                .uniqueResult();
    }
}
