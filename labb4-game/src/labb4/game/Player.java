package labb4.game;

import javax.swing.*;

public class Player {
    public final int id;
    public final String name;
    private final JLabel label;

    private int points;

    public Player(int id, String name, JLabel label) {
        this.id = id;
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
}
