package com.github.diosa.web4.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Math.pow;

// поле name = X задает имя таблицы как Х, а не аналогичное названию класса
@Entity
@Data
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Point.clearPoints", query = "DELETE FROM Point p WHERE p.user.username=:username"),
        @NamedQuery(name = "Point.getAllPoints", query = "SELECT p FROM Point p WHERE p.user.username=:username")
})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Point(double x, double y, double r, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.res = false;
        this.dateTime = LocalDateTime.now();
        this.user = user;
    }

    public String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    public double getY() {
        String strY = String.valueOf(this.y);
        int minus = strY.charAt(0) == '-' ? 1 : 0;
        return Double.parseDouble(strY.replace(",", "."));
    }

    private boolean rectangle(double x, double y, double r) {
        return (x >= 0) && (y >= 0) && (x <= r) && (y <= r / 2);
    }

    private boolean circle(double x, double y, double r) {
        return (x < 0) && (y < 0) && (pow(x, 2) + pow(y, 2) <= (pow(r / 2, 2)));
    }

    private boolean triangle(double x, double y, double r) {
        return (x < 0) && (y > 0) && (y < x + r);
    }

    public void checkHit() {
        if (circle(this.x, this.y, this.r)) {
            setMessage("Точка попала в четверть круга");
            ;
            setRes(true);
            return;
        }
        if (rectangle(this.x, this.y, this.r)) {
            setMessage("Точка попала в прямоугольник");
            setRes(true);
            return;
        }
        if (triangle(this.x, this.y, this.r)) {
            setMessage("Точка попала в треугольник");
            setRes(true);
            return;
        }
        setMessage("Точка не попала в область");
        setRes(false);
    }
}