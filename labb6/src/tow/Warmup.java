package tow;

class Warmup extends Level {

    Warmup() {

        goals.add(new Goal(50, 300));
        theCar = newCar(600, 300, RIGHT);
        theTrailer = newTrailer(RIGHT, theCar);
        name = "Warming up";

        obstacles.add(new IceObstacle(700, 300, 200, 150));
    }
}
