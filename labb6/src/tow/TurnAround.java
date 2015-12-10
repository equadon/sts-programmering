package tow;

class TurnAround extends Level {

    TurnAround() {

        goals.add(new Goal(50, 300));

        obstacles.add(new RectangleObstacle(0, 0, 200, 200));
        obstacles.add(new RectangleObstacle(0, 450, 200, 200));

        theCar = newCar(200, 300, LEFT);
        theTrailer = newTrailer(Direction.rotate(LEFT, 5), theCar);
        name = "Turn around";
    }
}
