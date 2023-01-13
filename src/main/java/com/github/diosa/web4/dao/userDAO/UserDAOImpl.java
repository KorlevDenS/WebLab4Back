package com.github.diosa.web4.dao.userDAO;

import com.github.diosa.web4.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserDAOImpl implements UserDAO {
    @PersistenceContext(unitName = "myUnit")
    private EntityManager entityManager;

    @Override
    public User create(User dto) {
        entityManager.persist(dto);
        return dto;
    }

    @Override
    public User update(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public User get(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User get(String username) {
        return entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
