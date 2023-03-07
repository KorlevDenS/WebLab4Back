package com.korolevs.weblab.web4.db;

import com.korolevs.weblab.web4.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class UserDAOImpl implements UserDAO {
    @PersistenceContext(unitName = "myUnit")
    private EntityManager entityManager;

    @Transactional
    @Override
    public User create(User dto) {
        entityManager.persist(dto);
        return dto;
    }

    @Transactional
    @Override
    public User updateAutheticated(User user) {
        entityManager.merge(user);
        entityManager.flush();
        return user;
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public User get(int id) {
        try {
            return entityManager.find(User.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User get(String username) {
        try {
            return entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User get(String username, String password) {
        try {
            return entityManager.createNamedQuery(User.FIND_BY_USERNAME_PASSWORD, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
