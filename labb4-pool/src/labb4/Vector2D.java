package labb4;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMagnitude() {
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
        x = x * factor;
        y = y * factor;

        return this;
    }

    public Vector2D divide(double denominator) {
        x = x / denominator;
        y = y / denominator;

        return this;
    }

    public Vector2D normalize() {
        x = x / getMagnitude();
        y = y / getMagnitude();

        return this;
    }

    public double dot(Vector2D vector) {
        return x * vector.x + y * vector.y;
    }

    public double distanceTo(Vector2D vector) {
        double xDiff = vector.x - x;
        double yDiff = vector.y - y;

        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    protected Vector2D clone() {
        return new Vector2D(x, y);
    }

    @Override
    public String toString() {
        return String.format("Vector2D[x=%.5f, y=%.5f]", x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Vector2D)) return false;

        Vector2D vector = (Vector2D) other;

        return x == vector.x && y == vector.y;
    }
}
