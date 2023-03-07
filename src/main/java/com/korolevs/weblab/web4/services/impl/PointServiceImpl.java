package com.korolevs.weblab.web4.services.impl;

import com.korolevs.weblab.web4.db.PointDAOImpl;
import com.korolevs.weblab.web4.model.Point;
import com.korolevs.weblab.web4.services.PointService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Math.pow;

public class PointServiceImpl implements PointService {
    @Inject
    private PointDAOImpl pointDAO;

    @Override
    public Point create(Point point, String username) {
        double startTime = System.nanoTime();
        point.setUsername(username);
        this.checkHit(point);
        point.setDateTime(LocalDateTime.now());
        point.setExecutionTime(System.nanoTime() - startTime);
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

    private boolean  isRectangle(double x, double y, double r) {
        if (r < 0) return (x < 0) && (y < 0) && (x >= r) && (y >= r/2);
        return (x > 0) && (y > 0) && (x <= r) && (y <= r/2);
    }

    private boolean isCircle(double x, double y, double r) {
        boolean circle = pow(x, 2) + pow(y, 2) <= (pow(r, 2));

        if (r < 0) return (x <= 0) && (y >= 0) && circle;
        return (x >= 0) && (y <= 0) && circle;
    }

    private boolean isTriangle(double x, double y, double r) {
        if (r < 0) return (x > 0) && (y < 0) && (y >= x + r/2);
        return (x < 0) && (y > 0) && (y <= x + r/2);
    }
}
