package labb4.game;

import javax.swing.*;

public class Player {
    public final String name;
    private final JLabel label;

    private int points;

    public Player(String name, JLabel label) {
        this.name = name;
        this.label = label;

        updateLabel();
    }

    public void reset() {
        points = 0;
        updateLabel();
    }

    public void addPoints(int points) {
        this.points += points;
        updateLabel();
    }

    private void updateLabel() {
        label.setText(name + ": " + points + " points");
    }

    @Override
    public String toString() {
        return "Player[" +
                "name='" + name + '\'' +
                ", points=" + points +
                ']';
    }
}
