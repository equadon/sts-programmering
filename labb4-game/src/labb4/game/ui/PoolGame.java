package labb4.game.ui;

import labb4.game.Config;
import labb4.game.GameType;
import labb4.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.util.List;
import java.util.ArrayList;

public class PoolGame {
    public static void main(String[] args) {
        JLabel turnLabel = new JLabel("Turn: ?");

        JPanel infoPanel = createInfoPanel(turnLabel);

        Player[] players = createPlayers();

        JFrame frame = new JFrame("Pool Game");
        PoolPanel poolPanel = new PoolPanel(frame, turnLabel, players);
        
        JMenuBar menu = createMenuBar(frame, poolPanel);
        frame.setJMenuBar(menu);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(poolPanel, BorderLayout.CENTER);
        frame.getContentPane().add(infoPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private static Player[] createPlayers() {
        List<Player> players = new ArrayList<>();

        int n = 1;

        boolean continueAsking = true;
        String name;
        while (n < 3) {// || continueAsking) {
            name = "Player " + n; //JOptionPane.showInputDialog("Enter name for player " + n);

            if (name == null || name.trim().equals("")) {
                continueAsking = false;
            } else {
                players.add(new Player(name));
                n++;
                continueAsking = true;
            }
        }

        return players.toArray(new Player[players.size()]);
    }

    private static JMenuBar createMenuBar(JFrame frame, PoolPanel poolPanel) {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = createGameMenu(frame, poolPanel);
        menuBar.add(gameMenu);

        JMenu debugMenu = createDebugMenu(frame, poolPanel);
        menuBar.add(debugMenu);

        return menuBar;
    }

    private static JMenu createGameMenu(JFrame frame, PoolPanel poolPanel) {
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('G');

        JMenuItem eightBall = new JMenuItem(new AbstractAction("Eight ball") {
            @Override
            public void actionPerformed(ActionEvent e) {
                poolPanel.newGame(GameType.EightBall);
            }
        });
        JMenuItem nineBall = new JMenuItem(new AbstractAction("Nine ball") {
            @Override
            public void actionPerformed(ActionEvent e) {
                poolPanel.newGame(GameType.NineBall);
            }
        });
        JMenuItem snooker = new JMenuItem(new AbstractAction("Snooker") {
            @Override
            public void actionPerformed(ActionEvent e) {
                poolPanel.newGame(GameType.Snooker);
            }
        });

        JMenuItem exit = new JMenuItem(new AbstractAction("Quit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        eightBall.setMnemonic('E');
        nineBall.setMnemonic('N');
        snooker.setMnemonic('S');
        exit.setMnemonic('Q');

        eightBall.setAccelerator(KeyStroke.getKeyStroke('1'));
        nineBall.setAccelerator(KeyStroke.getKeyStroke('2'));
        snooker.setAccelerator(KeyStroke.getKeyStroke('3'));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));

        gameMenu.add(eightBall);
        gameMenu.add(nineBall);
        gameMenu.add(snooker);
        gameMenu.addSeparator();
        gameMenu.add(exit);

        return gameMenu;
    }

    private static JMenu createDebugMenu(JFrame frame, PoolPanel poolPanel) {
        JMenu debugMenu = new JMenu("Debug");
        debugMenu.setMnemonic('D');

        JMenuItem boundingBoxes = new JCheckBoxMenuItem(new AbstractAction("Show bounding boxes") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();

                Config.DISPLAY_BOUNDING_BOXES = item.isSelected();
                poolPanel.repaint();
            }
        });

        boundingBoxes.setMnemonic('B');
        boundingBoxes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
        debugMenu.add(boundingBoxes);

        return debugMenu;
    }

    private static JPanel createInfoPanel(JLabel turnLabel) {
        JPanel infoPanel = new JPanel(new BorderLayout());

        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);

        infoPanel.add(turnLabel, BorderLayout.CENTER);

        return infoPanel;
    }
}
