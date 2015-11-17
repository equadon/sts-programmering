package labb4.game;

public class Player {
    public final String name;

    private int points;

    public Player(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void reset() {
        points = 0;
    }

    @Override
    public String toString() {
        return "Player[" +
                "name='" + name + '\'' +
                ", number=" + points +
                ']';
    }
}
