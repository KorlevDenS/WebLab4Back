package com.github.diosa.web4.services.impl;

import com.github.diosa.web4.dao.pointDAO.PointDAOImpl;
import com.github.diosa.web4.models.Point;
import com.github.diosa.web4.models.User;
import com.github.diosa.web4.services.PointService;

import javax.inject.Inject;
import java.util.List;

public class PointServiceImpl implements PointService {
    @Inject
    private PointDAOImpl pointDAO;

    @Override
    public Point create(Point point, User user) {
        point.setUser(user);
        point.setRes(this.isHit(point.getX(), point.getY(), point.getR()));
        point.setExecutionTime(System.nanoTime() - point.getExecutionTime());
        return pointDAO.create(point);
    }

    @Override
    public void deleteAllByUsername(String username) {
        pointDAO.deleteAllByUsername(username);
    }

    @Override
    public List<Point> getAllByUsername(String username) {
        return pointDAO.getAllByUsername(username);
    }

    private boolean isHit(double x, double y, double r) {
        return isRectangle(x, y, r) || isTriangle(x, y, r) || isCircle(x, y, r);
    }

    private boolean isRectangle(double x, double y, double r) {
        if (r >= 0) {
            return (x >= 0) && (y >= 0) && (x <= r) && (y <= r / 2);
        } else {
            return (x <= 0) && (y <= 0) && (x >= r) && (y >= r / 2);
        }
    }

    private boolean isCircle(double x, double y, double r) {
        boolean circle = (Math.pow(x, 2) + Math.pow(y, 2)) <= (Math.pow(r / 2, 2));
        if (r > 0) {
            return (x < 0) && (y < 0) && circle;
        } else {
            return (x > 0) && (y > 0) && circle;
        }
    }

    private boolean isTriangle(double x, double y, double r) {
        if (r > 0) {
            return (x < 0) && (y > 0) && (y < x + r);
        } else {
            return (x > 0) && (y < 0) && (y > x + r);
        }
    }
}
