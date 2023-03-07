package com.korolevs.weblab.web4.db;

import com.korolevs.weblab.web4.model.Point;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public interface PointDAO {
    Point create(Point dto);

    void deleteAllByUsername(String username);

    List<Point> getAllByUsername(String username);
}
