package labb4.game.ui;

import labb4.game.GameType;
import labb4.game.Player;
import labb4.game.interfaces.GameListener;
import labb4.game.interfaces.Placeable;
import labb4.game.tables.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class InfoPanel extends JPanel implements ActionListener, GameListener {
    private static final int WIDTH = 190;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);

    private final GamePanel gamePanel;
    private Table table;

    private JLabel turn;
    private JLabel scores;
    private JLabel nextBall;

    public InfoPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.table = gamePanel.getTable();

        setPreferredSize(new Dimension(WIDTH, 200));

        add(new JButton(new AbstractAction("Eight Ball") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gamePanel.newGame(GameType.EightBall, true);
                setTable(gamePanel.getTable());
            }
        }));

        add(new JButton(new AbstractAction("Nine Ball") {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gamePanel.newGame(GameType.NineBall, true);
                setTable(gamePanel.getTable());
            }
        }));

        add(new JButton(new AbstractAction("Snooker") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gamePanel.newGame(GameType.Snooker, true);
                setTable(gamePanel.getTable());
            }
        }));

        JLabel turnTitle = new JLabel("Turn:");
        turnTitle.setFont(TITLE_FONT);
        turnTitle.setPreferredSize(new Dimension(WIDTH, 30));
        add(turnTitle);

        turn = new JLabel("?");
        turn.setPreferredSize(new Dimension(WIDTH, 30));
        add(turn);

        JLabel nextBallTitle = new JLabel("Next ball:");
        nextBallTitle.setFont(TITLE_FONT);
        nextBallTitle.setPreferredSize(new Dimension(WIDTH, 30));
        add(nextBallTitle);

        nextBall = new JLabel("?");
        nextBall.setPreferredSize(new Dimension(WIDTH, 30));
        add(nextBall);

        JLabel pointsTitle = new JLabel("Points:");
        pointsTitle.setFont(TITLE_FONT);
        pointsTitle.setPreferredSize(new Dimension(WIDTH, 30));

        add(pointsTitle);

        scores = new JLabel("");
        scores.setFont(new Font("Arial", Font.PLAIN, 12));
        scores.setPreferredSize(new Dimension(WIDTH, 300));
        scores.setVerticalAlignment(SwingConstants.TOP);
        add(scores);

        table.addListener(this);

        updateScores();
    }

    public void setTable(Table table) {
        this.table = table;
        table.addListener(this);
        updateScores();
        playerChanged(table.getCurrentPlayer());
        nextBall("-");
    }

    private void updateScores() {
        StringBuilder sb = new StringBuilder();

        Player[] players = Arrays.copyOf(table.getPlayers(), table.getPlayers().length);
        Arrays.sort(players, Collections.reverseOrder());

        int n = 1;
        for (Player player : players) {
            sb.append(String.format("%d. <b>%s</b>  &nbsp;&nbsp; <i>%d point(s)</i><br>", n, player.name, player.getPoints()));
            n++;
        }

        scores.setText("<html>" + sb.toString() + "</html>");
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
        //JOptionPane.showMessageDialog(this, "It's " + newPlayer.name + " turn.", "Change Player", JOptionPane.INFORMATION_MESSAGE);
        updateScores();
    }

    @Override
    public void pointsAdded(Player player, int points) {
        updateScores();
    }

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
