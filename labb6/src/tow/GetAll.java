package tow;

class GetAll extends Level {

    GetAll() {

        goals.add(new Goal(50, 300));
        goals.add(new Goal(50, 450));
        goals.add(new Goal(50, 600));

        obstacles.add(new RectangleObstacle(0, 375, 75, borderWidth));
        obstacles.add(new RectangleObstacle(0, 525, 75, borderWidth));

        theCar = newCar(400, 300, RIGHT);
        theTrailer = newTrailer(RIGHT, theCar);
        name = "Get all";
    }
}
