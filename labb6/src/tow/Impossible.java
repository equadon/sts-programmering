package tow;

class Impossible extends Level {

    Impossible() {

        goals.add(new Goal(1000 * 7 / 8, 1000 / 8));

        theCar = newCar(700, 300, RIGHT);
        Trailer middle = newTrailer(RIGHT, theCar);
        theTrailer = newTrailer(RIGHT, middle);
        name = "Is this possible?";
    }
}
