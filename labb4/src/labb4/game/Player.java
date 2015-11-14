package labb4.game;

import labb4.game.interfaces.TableListener;

import javax.swing.*;

public class Player implements TableListener {
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
    }

    private void updateLabel() {
        label.setText(name + ": " + points + " points");
    }

    @Override
    public void turnChanged(Player currentPlayer) {}

    @Override
    public void pointsAdded(Player player, int points) {
        if (player == this) {
            this.points += points;
            updateLabel();
        }
    }

    @Override
    public void gameCreated() {
        points = 0;
        updateLabel();
    }
}
