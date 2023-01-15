package com.github.diosa.web4.db.pointDAO;

import com.github.diosa.web4.models.Point;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PointDAOImpl implements PointDAO {
    @PersistenceContext(unitName = "myUnit")
    private EntityManager entityManager;

    @Transactional
    @Override
    public Point create(Point dto) {
        entityManager.persist(dto);
        return dto;
    }

    @Transactional
    @Override
    public void deleteAllByUsername(String username) {
        entityManager.createNamedQuery(Point.CLEAR_POINTS)
                .setParameter("username", username)
                .executeUpdate();
    }

    @Override
    public List<Point> getAllByUsername(String username) {
        try {
            return entityManager.createNamedQuery(Point.GET_ALL_POINTS, Point.class)
                    .setParameter("username", username)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
