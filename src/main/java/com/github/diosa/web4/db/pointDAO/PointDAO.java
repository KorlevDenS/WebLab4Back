package com.github.diosa.web4.db.pointDAO;

import com.github.diosa.web4.models.Point;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public interface PointDAO {
    Point create(Point dto);

    void deleteAllByUsername(String username);

    List<Point> getAllByUsername(String username);
}
