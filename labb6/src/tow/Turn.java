package tow;

class Turn extends Level {

    Turn() {

        goals.add(new Goal(50, 300));

        obstacles.add(new RectangleObstacle(0, 0, 200, 200));

        theCar = newCar(400, 500, DOWN);
        theTrailer = newTrailer(DOWN, theCar);
        name = "turn it";
    }
}
