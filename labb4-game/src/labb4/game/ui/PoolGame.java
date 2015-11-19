package labb4.game.ui;

import labb4.game.Config;
import labb4.game.GameType;
import labb4.game.Player;
import labb4.game.objects.Ball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.util.List;
import java.util.ArrayList;

public class PoolGame extends JFrame {
    public PoolGame() {
        setLayout(new BorderLayout());

        Player[] players = createPlayers();

        PoolPanel poolPanel = new PoolPanel(this, players);

        setJMenuBar(createMenuBar(poolPanel));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        add(poolPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Player[] createPlayers() {
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

    private JMenuBar createMenuBar(PoolPanel poolPanel) {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = createGameMenu(poolPanel);
        JMenu debugMenu = createDebugMenu(poolPanel);

        menuBar.add(gameMenu);
        menuBar.add(debugMenu);

        return menuBar;
    }

    private JMenu createGameMenu(PoolPanel poolPanel) {
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
                dispose();
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

    private JMenu createDebugMenu(PoolPanel poolPanel) {
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

        JMenuItem hideBalls = new JCheckBoxMenuItem(new AbstractAction("Hide balls") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();

                Config.HIDE_BALLS = item.isSelected();
                if (Config.HIDE_BALLS) {
                    for (Ball ball : poolPanel.getTable().getBalls())
                        ball.hide();
                } else {
                    for (Ball ball : poolPanel.getTable().getBalls())
                        ball.show();
                }
                poolPanel.repaint();
            }
        });

        JMenuItem velocityVectors = new JCheckBoxMenuItem(new AbstractAction("Show velocity vectors") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();

                Config.DISPLAY_VELOCITY_VECTORS = item.isSelected();
            }
        });

        boundingBoxes.setMnemonic('B');
        hideBalls.setMnemonic('H');
        velocityVectors.setMnemonic('V');

        boundingBoxes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
        hideBalls.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
        velocityVectors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));

        debugMenu.add(boundingBoxes);
        debugMenu.add(velocityVectors);
        debugMenu.add(hideBalls);

        return debugMenu;
    }

    public static void main(String[] args) {
        new PoolGame();
    }
}
