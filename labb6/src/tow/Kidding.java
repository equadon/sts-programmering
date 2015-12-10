package tow;

class Kidding extends Level {

    Kidding() {

        goals.add(new Goal(1000 * 7 / 8, 1000 / 8));

        theCar = newCar(700, 300, RIGHT);
        Trailer middle = newTrailer(RIGHT, theCar);
        Trailer middle2 = newTrailer(RIGHT, middle);
        theTrailer = newTrailer(RIGHT, middle2);
        name = "You are kidding!";
    }
}
