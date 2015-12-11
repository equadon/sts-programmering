package tow;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

class Game extends JFrame {

    private final int FIRST_LEVEL = 0;
    private final double FIRST_SCALE = 1.0;

    private GameArea gameArea;
    private Header header;
    private Buttons buttons;

    private Clip crashSound, demolishSound;
    private Clip[] fanfares = new Clip[3];

    private double scale;
    private int level;
    private String levelName;

    Game() {

        super();

        String laf = UIManager.getCrossPlatformLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (Exception e) {
            System.err.println("Error loading L&F: " + e.getMessage());
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        setSounds();

        level = FIRST_LEVEL;
        scale = FIRST_SCALE;

        launch();
    }

    private void setSounds() {

        crashSound = getSound("crash.wav");
        demolishSound = getSound("crash3.wav");
        fanfares[0] = getSound("fanfare1.wav");
        fanfares[1] = getSound("fanfare2.wav");
        fanfares[2] = getSound("fanfare3.wav");
    }

    private Clip getSound(String name) {

        Clip clip;
        try {
            URL url = new URL("http://user.it.uu.se/~joachim/Tow/" + name);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        } catch (Exception e) {
            return null;
        }
    }

    private void launch() {

        Container pane = getContentPane();
        pane.removeAll();

        gameArea = new GameArea(this, scale, level);
        pane.add(gameArea, BorderLayout.CENTER);

        levelName = LevelFactory.getName(level);
        int maxTime = gameArea.getMaxTime();
        header = new Header(this, levelName, maxTime);
        pane.add(header, BorderLayout.NORTH);

        setTitle(levelName);

        buttons = new Buttons(this);
        pane.add(buttons, BorderLayout.EAST);

        pack();
        setVisible(true);
        gameArea.setFocusable(true);
        refocus();
    }

    private void playSound(Clip clip) {
        if (!Tow.enableSound)
            return;

        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    private void playRandomFanfare() {

        int fanfareNumber = (int) (Math.random() * 3);
        playSound(fanfares[fanfareNumber]);
    }

    void refocus() {

        gameArea.requestFocusInWindow();
    }

    private void stop() {

        header.stop();
        gameArea.stop();
    }

    void relaunch() {

        stop();
        launch();
    }

    void launchSize(double s) {

        scale = s;
        relaunch();
    }

    void launchLevel(int lev) {

        level = lev;
        relaunch();
    }

    void launchRandom() {

        level = LevelFactory.maxLevel + 1;
        relaunch();
    }

    void timeout() {

        stop();
        JOptionPane.showMessageDialog(this, "Sorry, you took too long. Click OK to restart level");
        launch();
    }

    void tryToGo(String attempt) {

        int target = LevelFactory.findLevel(attempt);
        if (target == LevelFactory.NO_SUCH_LEVEL) {
            JOptionPane.showMessageDialog(this, "No such level. Type the name of a level, precisely as shown when you visited it.");
        } else {
            level = target;
            relaunch();
        }
    }

    void crash(String crashMessage) {

        playSound(crashSound);
        JOptionPane.showMessageDialog(this, crashMessage);
    }

    void crashOut() {

        stop();
        playSound(demolishSound);
        JOptionPane.showMessageDialog(this, "CRAAAAASSSH. I warned you. Yor car broke down.... Click OK to restart level");
        launch();
    }

    void succeed() {

        stop();
        playRandomFanfare();
        int runTime = header.runTime();
        if (level >= LevelFactory.maxLevel) {
            JOptionPane.showMessageDialog(this, "Congratulations! You finished it in " + runTime +
                    " seconds. Try it again!");
        } else {
            level++;
            JOptionPane.showMessageDialog(this, "Congratulations! You finished in " + runTime +
                    " seconds.  Now go to the next level: " + LevelFactory.getName(level));
        }
        launch();
    }
}
