package com.github.diosa.web4.services.impl;

import com.github.diosa.web4.db.pointDAO.PointDAOImpl;
import com.github.diosa.web4.models.Point;
import com.github.diosa.web4.services.PointService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

public class PointServiceImpl implements PointService {
    @Inject
    private PointDAOImpl pointDAO;

    @Override
    public Point create(Point point, String username) {
        point.setUsername(username);
        this.checkHit(point);
        point.setDateTime(LocalDateTime.now());
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


    private void checkHit(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();
        if (isCircle(x, y, r)) {
            point.setMessage("Точка попала в четверть круга");
            point.setRes(true);
            return;
        }
        if (isRectangle(x, y, r)) {
            System.out.println("isRectangle");
            point.setMessage("Точка попала в прямоугольник");
            point.setRes(true);
            return;
        }
        if (isTriangle(x, y, r)) {
            point.setMessage("Точка попала в треугольник");
            point.setRes(true);
            return;
        }
        point.setMessage("Точка не попала в область");
        point.setRes(false);
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
