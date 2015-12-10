package tow;

class Tricky extends Level {

    Tricky() {

        goals.add(new Goal(1000 * 7 / 8, 1000 / 8));

        obstacles.add(new RectangleObstacle(1000 / 2, 0, borderWidth, 1000 * 2 / 3));
        obstacles.add(new RectangleObstacle(1000 * 3 / 4, 1000. / 4, 1000 / 4, borderWidth));

        theCar = newCar(400, 300, RIGHT);
        theTrailer = newTrailer(RIGHT, theCar);
        name = "Tricky parking";
    }
}
