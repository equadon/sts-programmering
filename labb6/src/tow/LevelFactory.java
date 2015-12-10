package tow;

class LevelFactory {

    static final int maxLevel = 13;
    static final int NO_SUCH_LEVEL = -1;

    static Level generateLevel(int levelNo) {

        switch (levelNo) {
            case 0:
                return new Warmup();
            case 1:
                return new Steer();
            case 2:
                return new Turn();
            case 3:
                return new TurnAround();
            case 4:
                return new EasyPark();
            case 5:
                return new GetAll();
            case 6:
                return new Precision();
            case 7:
                return new Straight();
            case 8:
                return new Curve();
            case 9:
                return new Corner();
            case 10:
                return new CornerOut();
            case 11:
                return new Tricky();
            case 12:
                return new Impossible();
            case 13:
                return new Kidding();
            default:
                return new RandomRoad();
        }
    }

    static String getName(int levelNo) {
        return generateLevel(levelNo).name;
    }

    static int findLevel(String attempt) {
        for (int i = 0; i <= maxLevel; i++) {
            if (getName(i).equals(attempt)) {
                return i;
            }
        }
        return NO_SUCH_LEVEL;
    }
}
