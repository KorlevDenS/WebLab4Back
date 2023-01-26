package com.github.diosa.web4.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static java.lang.Math.pow;

// поле name = X задает имя таблицы как Х, а не аналогичное названию класса
@Entity
@Table(name = "point")
@NamedQueries({
        @NamedQuery(name = "Point.clearPoints", query = "DELETE FROM Point p WHERE p.username=:username"),
        @NamedQuery(name = "Point.getAllPoints", query = "SELECT p FROM Point p WHERE p.username=:username")
})
@Data
@NoArgsConstructor
public class Point {
    public static final String CLEAR_POINTS = "Point.clearPoints";
    public static final String GET_ALL_POINTS = "Point.getAllPoints";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double x;
    private double y;
    private double r;
    private boolean res;
    private double executionTime;
    private String message;
    private LocalDateTime dateTime;
    private String username;

    public String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    public double getY() {
        String strY = String.valueOf(this.y);
        int minus = strY.charAt(0) == '-' ? 1 : 0;
        return Double.parseDouble(strY.replace(",", "."));
    }
}