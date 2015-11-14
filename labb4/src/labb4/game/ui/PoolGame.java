package labb4.game.ui;

import javax.swing.*;

public class PoolGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pool Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(new PoolPanel(frame));
        frame.pack();
        frame.setVisible(true);
    }
}
