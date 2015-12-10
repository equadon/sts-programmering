package tow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Header extends JPanel {

    private class TimeKeeper extends JPanel implements ActionListener {

        private int maxTime;
        private int timeLeft;
        private final Color ARC_COLOR = Color.RED;
        private final int SIZE = 75;

        TimeKeeper(int maxTime) {

            this.maxTime = maxTime;
            timeLeft = maxTime;
            setPreferredSize(new Dimension(SIZE, SIZE));
        }

        int runTime() {

            return maxTime - timeLeft;
        }

        @Override
        public void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // This makes the graphics smoother
                    RenderingHints.VALUE_ANTIALIAS_ON);
            final int OFFSET = 12;
            final int ARC_SIZE = 50;
            int angle = (360 * timeLeft) / maxTime;
            g2.setColor(ARC_COLOR);
            g2.fillArc(OFFSET, 2 * OFFSET, ARC_SIZE, ARC_SIZE, 90, angle);
            g2.setColor(Color.BLACK);
            g2.drawString("Time:", (int) (1.5 * OFFSET), OFFSET);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            timeLeft--;
            Header.this.repaint();
            if (timeLeft == 0) {
                game.timeout();
            }
        }
    }


    private Game game;
    private JLabel levelName = new JLabel();
    private TimeKeeper timeKeeper;
    private Timer clock;


    Header(Game g, String name, int maxTime) {

        game = g;

        setLayout(new FlowLayout(FlowLayout.CENTER));
        levelName.setText(name);
        levelName.setFont(new Font("Times", Font.BOLD, 64));
        add(levelName);

        add(Box.createRigidArea(new Dimension(100, 0)));
        timeKeeper = new TimeKeeper(maxTime);
        add(timeKeeper);
        clock = new Timer(1000, timeKeeper);
        clock.start();
    }

    int runTime() {

        return timeKeeper.runTime();
    }

    void stop() {

        clock.stop();
    }
}
