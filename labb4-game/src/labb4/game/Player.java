package labb4.game;

public class Player implements Comparable<Player> {
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

    /**
     * Compare players based on points.
     */
    @Override
    public int compareTo(Player other) {
        if (points == other.points)
            return 0;
        return points - other.points;
    }

    @Override
    public String toString() {
        return "Player[" +
                "name='" + name + '\'' +
                ", points=" + points +
                ']';
    }
}
