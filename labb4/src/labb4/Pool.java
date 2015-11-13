package labb4;

import javax.swing.*;

/**
 * Uppgift 1, labb 4: Karin Melin och Niklas Persson
 */

public class Pool {
    final static int UPDATE_FREQUENCY = 100;    // Global constant: fps, ie times per second to simulate

    public static void main(String[] args) {

        JFrame frame = new JFrame("Collisions!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Table table = new Table();

        frame.getContentPane().add(table);
        frame.pack();
        frame.setVisible(true);
    }
}
