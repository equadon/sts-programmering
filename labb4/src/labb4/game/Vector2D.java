package labb4.game;

import java.awt.event.MouseEvent;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D add(Vector2D vector) {
        x += vector.x;
        y += vector.y;

        return this;
    }

    public Vector2D subtract(Vector2D vector) {
        x -= vector.x;
        y -= vector.y;

        return this;
    }

    public Vector2D multiply(double factor) {
        x *= factor;
        y *= factor;

        return this;
    }

    public Vector2D divide(double factor) {
        x = x / factor;
        y = x / factor;

        return this;
    }

    public double distanceTo(Vector2D vector) {
        double xDiff = x - vector.x;
        double yDiff = y - vector.y;

        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public double dot(Vector2D vector) {
        return x * vector.x + y * vector.y;
    }

    public Vector2D normalize() {
        double length = length();

        x = x / length;
        y = y / length;

        return this;
    }

    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2D vector2D = (Vector2D) o;

        if (Double.compare(vector2D.x, x) != 0) return false;
        return Double.compare(vector2D.y, y) == 0;

    }

    @Override
    public String toString() {
        return "Vector2D[" + "x=" + x + ", y=" + y + ']';
    }

    public static Vector2D zero() {
        return new Vector2D(0, 0);
    }

    public static Vector2D fromMouseEvent(MouseEvent event) {
        return new Vector2D(event.getX(), event.getY());
    }
}
