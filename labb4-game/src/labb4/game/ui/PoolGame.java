package labb4.game.ui;

import labb4.game.Player;

import javax.swing.*;
import java.awt.*;

public class PoolGame {
    public static void main(String[] args) {
        JPanel infoPanel = new JPanel(new BorderLayout());
        JLabel player1Points = new JLabel("");
        JLabel player2Points = new JLabel("");

        //String player1Name = JOptionPane.showInputDialog("Enter name for player 1:");
        //String player2Name = JOptionPane.showInputDialog("Enter name for player 2:");

        Player player1 = new Player("Jane", player1Points);
        Player player2 = new Player("Joe", player2Points);

        JLabel turnLabel = new JLabel("Turn: " + player1.name);
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);

        infoPanel.add(player1Points, BorderLayout.WEST);
        infoPanel.add(turnLabel, BorderLayout.CENTER);
        infoPanel.add(player2Points, BorderLayout.EAST);

        JFrame frame = new JFrame("Pool Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(new PoolPanel(frame, player1, player2, turnLabel), BorderLayout.CENTER);
        frame.getContentPane().add(infoPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}
