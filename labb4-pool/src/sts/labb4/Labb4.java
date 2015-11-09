package sts.labb4;

import javax.swing.*;

public class Labb4 {
    public final static int UPDATE_FREQUENCY = 100;    // Global constant: fps, ie times per second to simulate

    public static void main(String[] args) {

        JFrame frame = new JFrame("No collisions!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Table table = new Table();

        frame.getContentPane().add(table);
        frame.pack();
        frame.setVisible(true);
    }
}
