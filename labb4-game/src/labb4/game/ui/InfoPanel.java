package labb4.game.ui;

import labb4.game.Player;
import labb4.game.interfaces.GameListener;
import labb4.game.interfaces.Placeable;
import labb4.game.tables.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends JPanel implements ActionListener, GameListener {
    private static final int WIDTH = 250;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);

    private final GamePanel gamePanel;
    private final Table table;

    private JLabel turn;
    private JLabel nextBall;

    public InfoPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.table = gamePanel.getTable();

        setPreferredSize(new Dimension(WIDTH, 200));

        JLabel turnTitle = new JLabel("Turn:");
        turnTitle.setFont(TITLE_FONT);
        turnTitle.setPreferredSize(new Dimension(WIDTH, 30));
        add(turnTitle);

        turn = new JLabel("?");
        turn.setPreferredSize(new Dimension(WIDTH, 30));
        add(turn);

        JLabel pointsTitle = new JLabel("Points:");
        pointsTitle.setFont(TITLE_FONT);
        pointsTitle.setPreferredSize(new Dimension(WIDTH, 30));
        add(pointsTitle);

        JLabel nextBallTitle = new JLabel("Next ball:");
        nextBallTitle.setFont(TITLE_FONT);
        nextBallTitle.setPreferredSize(new Dimension(WIDTH, 30));
        add(nextBallTitle);

        nextBall = new JLabel("?");
        nextBall.setPreferredSize(new Dimension(WIDTH, 30));
        add(nextBall);

        table.addListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void illegalMove(String reason) {
        JOptionPane.showMessageDialog(this, reason, "Invalid Move", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void playerChanged(Player newPlayer) {
        turn.setText(newPlayer.name);
        JOptionPane.showMessageDialog(this, "It's " + newPlayer.name + " turn.", "Change Player", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void pointsAdded(Player player, int points) {}

    @Override
    public void placeStarted(Placeable placeable) {}

    @Override
    public void nextBall(String next) {
        nextBall.setText(next);
    }

    @Override
    public void gameEnded(Player winner) {
        JOptionPane.showMessageDialog(this, winner.name + " won the game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}
