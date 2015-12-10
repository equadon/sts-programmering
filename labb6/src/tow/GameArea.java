package tow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class GameArea extends JPanel implements ActionListener {

    static double scale;

    static int sizeX;
    static int sizeY;

    private final Timer timer;
    private final int UPDATE_FREQUENCY = 30;

    private Car car;
    private Trailer trailer;

    private Cockpit cockpit;

    private Level level;

    private int levelNumber;
    private int collisionsLeft;

    private final Game game;

    private final int MAX_COLLISIONS = 5;

    GameArea(Game g, double scaleFactor, int levNumber) {

        super();

        scale = scaleFactor;

        game = g;
        levelNumber = levNumber;

        sizeX = (int) (1000 * scale);
        sizeY = (int) (1000 * scale);


        level = LevelFactory.generateLevel(levelNumber);
        car = level.theCar;
        trailer = level.theTrailer;

        cockpit = new Cockpit();
        collisionsLeft = MAX_COLLISIONS;

        setPreferredSize(new Dimension(sizeX, sizeY));

        addKeyListener(cockpit);

        addKeyListener(new KeyAdapter() {  //For debugging: type Q to go to next level!
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.getKeyChar() == 'Q') {
                    game.succeed();
                }
            }
        });

        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();

        timer = new Timer(1000 / UPDATE_FREQUENCY, this);
        timer.start();
        repaint();
    }


    void stop() {

        timer.stop();
    }

    String getLevelName() {

        return level.name;
    }

    int getMaxTime() {

        return level.maxTime;
    }


    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // This makes the graphics smoother
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.gray);
        g2.fillRect(0, 0, sizeX, sizeY);

        level.paint(g2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        cockpit.changeState();

        double velocity = cockpit.getVelocity();
        int angle = cockpit.getSteeringWheelAngle();

        if (!car.moveSuccessfully(velocity, angle)) {
            crash();
        }

        repaint();
        level.checkGoals(trailer);
        if (level.allGoalsAreReached()) {
            game.succeed();
        }
    }

    private void crash() {
        repaint();
        if (collisionsLeft == 1) {
            game.crashOut();
        } else {
            collisionsLeft--;
            cockpit.resetKeys();
            if (collisionsLeft == 1) {
                game.crash("CRASH! CAREFUL! NEXT CRASH YOU BREAK DOWN!");
            } else {
                game.crash("CRASH! Be careful. Your car will break down after " + collisionsLeft + " more crashes");
            }
        }
    }
}
