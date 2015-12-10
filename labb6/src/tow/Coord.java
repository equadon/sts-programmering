package tow;

class Coord {

    double x, y;

    Coord(double x, double y) {                // A new coordinate just records x and y
        this.x = x;
        this.y = y;
    }

    double magnitude() {                        // magnitude, ie distance from origo
        return Math.sqrt(x * x + y * y);
    }

    Direction norm() {                              // norm: a normalised vector at the same direction
        return new Direction(x / magnitude(), y / magnitude());
    }

    static Coord add(Coord a, Coord b) {        // vector addition
        return new Coord(a.x + b.x, a.y + b.y);
    }

    static Coord sub(Coord a, Coord b) {        // vector subtraction
        return new Coord(a.x - b.x, a.y - b.y);
    }

    static Coord mul(double c, Coord a) {       // multiplication by a constant
        return new Coord(c * a.x, c * a.y);
    }

    static Coord middle(Coord a, Coord b) {     // midpoint
        return mul(0.5, add(a, b));
    }
}
