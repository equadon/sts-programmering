package labb4.game.ui;

import labb4.game.GameType;
import labb4.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PoolGame {
    public static void main(String[] args) {
        JLabel player1Points = new JLabel("");
        JLabel player2Points = new JLabel("");

        Player player1 = new Player("Player 1", player1Points);
        Player player2 = new Player("Player 2", player2Points);

        JLabel turnLabel = new JLabel("Turn: " + player1.name);

        JPanel infoPanel = createInfoPanel(player1, player1Points, player2Points, turnLabel);

        JFrame frame = new JFrame("Pool Game");
        PoolPanel poolPanel = new PoolPanel(frame, player1, player2, turnLabel);
        
        JMenuBar menu = createMenuBar(poolPanel);
        frame.setJMenuBar(menu);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(poolPanel, BorderLayout.CENTER);
        frame.getContentPane().add(infoPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private static JMenuBar createMenuBar(PoolPanel poolPanel) {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        gameMenu.add(new JMenuItem(new AbstractAction("Eight ball") {
            @Override
            public void actionPerformed(ActionEvent e) {
                poolPanel.newGame(GameType.EightBall);
            }
        }));

        gameMenu.add(new JMenuItem(new AbstractAction("Nine ball") {
            @Override
            public void actionPerformed(ActionEvent e) {
                poolPanel.newGame(GameType.NineBall);
            }
        }));

        gameMenu.add(new JMenuItem(new AbstractAction("Snooker") {
            @Override
            public void actionPerformed(ActionEvent e) {
                poolPanel.newGame(GameType.Snooker);
            }
        }));

        return menuBar;
    }

    private static JPanel createInfoPanel(Player player1, JLabel player1Points, JLabel player2Points, JLabel turnLabel) {
        JPanel infoPanel = new JPanel(new BorderLayout());

        //String player1Name = JOptionPane.showInputDialog("Enter name for player 1:");
        //String player2Name = JOptionPane.showInputDialog("Enter name for player 2:");

        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);

        infoPanel.add(player1Points, BorderLayout.WEST);
        infoPanel.add(turnLabel, BorderLayout.CENTER);
        infoPanel.add(player2Points, BorderLayout.EAST);

        return infoPanel;
    }
}
