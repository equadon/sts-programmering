package tow;

class CornerOut extends Level {

    CornerOut() {

        goals.add(new Goal(50, 300));

        obstacles.add(new RectangleObstacle(0, 0, 400, 225));
        obstacles.add(new RectangleObstacle(0, 400, 400, 1000 - 400));
        obstacles.add(new RectangleObstacle(700, 0, 1000 - 700, 1000));


        theCar = newCar(100, 300, LEFT);
        theTrailer = newTrailer(LEFT, theCar);
        name = "Out of corner";
    }
}
