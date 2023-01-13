package com.github.diosa.web4.services;

import com.github.diosa.web4.models.Point;
import com.github.diosa.web4.models.User;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public interface PointService {
    Point create(Point dto, User user);

    void deleteAllByUsername(String username);

    List<Point> getAllByUsername(String username);
}
