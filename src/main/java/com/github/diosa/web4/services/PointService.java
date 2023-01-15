package com.github.diosa.web4.services;

import com.github.diosa.web4.models.Point;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public interface PointService {
    Point create(Point dto, String username);

    void deleteAllByUsername(String username);

    List<Point> getAllByUsername(String username);
}
