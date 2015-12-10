package tow;

class Direction extends Coord {     // direction as unit vector

    Direction(double x, double y) {
        super(x, y);
    }

    static Direction rotate(Direction a, double angle) {   // rotating a unit vector
        angle = angle * Math.PI / 180;
        Direction rotation = new Direction(Math.cos(angle), Math.sin(angle));
        return complexMult(a, rotation);
    }

    static double angle(Direction a, Direction b) {    // angle between directions
        return 180 / Math.PI * Math.acos(scal(a, b) / (a.magnitude() * b.magnitude()));
    }

    private static Direction complexMult(Direction a, Direction b) {    // multiplication of complex numbers
        return new Direction(a.x * b.x - a.y * b.y, a.x * b.y + a.y * b.x);
    }

    private static double scal(Direction a, Direction b) {      // scalar product
        return a.x * b.x + a.y * b.y;
    }
}
