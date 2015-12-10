package tow;

class EasyPark extends Level {

    EasyPark() {

        goals.add(new Goal(50, 300));

        obstacles.add(new RectangleObstacle(0, 400, 300, borderWidth));
        obstacles.add(new RectangleObstacle(300 - borderWidth, 300, borderWidth, 100));

        theCar = newCar(900, 300, RIGHT);
        theTrailer = newTrailer(Direction.rotate(RIGHT, 20), theCar);
        name = "Easy parking";
    }
}
