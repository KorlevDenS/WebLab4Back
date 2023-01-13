package com.github.diosa.web4.dao.pointDAO;

import com.github.diosa.web4.models.Point;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class PointDAOImpl implements PointDAO {
    @PersistenceContext(unitName = "myUnit")
    private EntityManager entityManager;

    @Override
    public Point create(Point dto) {
        entityManager.persist(dto);
        return dto;
    }

    @Override
    public void deleteAllByUsername(String username) {
        entityManager.createNamedQuery(Point.CLEAR_POINTS)
                .setParameter("username", username)
                .executeUpdate();
    }

    @Override
    public List<Point> getAllByUsername(String username) {
        return entityManager.createNamedQuery(Point.GET_ALL_POINTS, Point.class)
                .setParameter("username", username)
                .getResultList();
    }
}
