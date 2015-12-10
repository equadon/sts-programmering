package tow;

class Steer extends Level {

    Steer() {

        goals.add(new Goal(50, 300));

        obstacles.add(new RectangleObstacle(0, 0, 200, 200));

        theCar = newCar(600, 300, Direction.rotate(RIGHT, -20));
        theTrailer = newTrailer(Direction.rotate(RIGHT, -20), theCar);
        name = "Steer it";
    }
}
