package tow;

class Precision extends Level {

    Precision() {

        goals.add(new Goal(10, 450));

        obstacles.add(new RectangleObstacle(0, 410, 150, borderWidth));
        obstacles.add(new RectangleObstacle(0, 515, 150, borderWidth));

        theCar = newCar(400, 300, RIGHT);
        theTrailer = newTrailer(RIGHT, theCar);
        name = "Precision parking";
    }
}
