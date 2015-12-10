package tow;

class Straight extends Level {

    Straight() {

        goals.add(new Goal(50, 300));

        obstacles.add(new RectangleObstacle(0, 0, 500, 225));
        obstacles.add(new RectangleObstacle(0, 385, 500, 1000 - 385));

        theCar = newCar(900, 300, RIGHT);
        theTrailer = newTrailer(Direction.rotate(RIGHT, 5), theCar);
        name = "Run straight";
    }
}
