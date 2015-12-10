package tow;

import java.awt.*;

class Trailer extends Vehicle {

    Trailer(Coord frontAxisPos, double lengthBetweenAxes, Direction direction, double frontLength, double rearLength, double width, Color color, Level lev) {

        super(frontAxisPos, lengthBetweenAxes, direction, frontLength, rearLength, width, color, lev);
    }

    @Override
    Polygon appearance() {

        Coord widthOffset = Coord.mul(width, Direction.rotate(direction, 90));
        Coord boomEndpoint = Coord.middle(frontAxisPos, rearAxisPos);
        Coord frontLeft = Coord.add(boomEndpoint, widthOffset);
        Coord frontRight = Coord.sub(boomEndpoint, widthOffset);
        Coord boomLeft = Coord.middle(frontLeft, boomEndpoint);
        Coord boomRight = Coord.middle(frontRight, boomEndpoint);
        Coord rearLeft = Coord.add(rearPos, widthOffset);
        Coord boomConnector = Coord.sub(frontAxisPos, Coord.mul(3, direction));
        Coord rearRight = Coord.sub(rearPos, widthOffset);

        int[] xpoints = {(int) frontLeft.x, (int) rearLeft.x, (int) rearRight.x, (int) frontRight.x, (int) boomRight.x, (int) boomConnector.x, (int) boomLeft.x};
        int[] ypoints = {(int) frontLeft.y, (int) rearLeft.y, (int) rearRight.y, (int) frontRight.y, (int) boomRight.y, (int) boomConnector.y, (int) boomLeft.y};

        return new Polygon(xpoints, ypoints, 7);
    }
}
