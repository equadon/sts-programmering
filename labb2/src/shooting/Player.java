package shooting;

import shooting.strategies.Strategy;

import java.util.logging.Logger;

public class Player {
    private static final Logger LOG = Logger.getLogger(Player.class.getName());

    public final String name;
    public final double accuracy;
    private final Strategy strategy;

    private int lives;

    public Player(String name, double accuracy, int lives, Strategy strategy) {
        this.name = name;
        this.accuracy = accuracy;
        this.lives = lives;
        this.strategy = strategy;
    }

    public int getLives() {
        return lives;
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public boolean isDead() {
        return !isAlive();
    }

    private void kill() {
        lives--;
    }

    public Player shoot() {
        // Find and shoot victim
        Player victim = strategy.choose(this);
        return shoot(victim);
    }

    public Player shoot(Player victim) {
        LOG.fine(name + " shoots at " + ((victim == null) ? "nobody" : victim.name));

        if (victim != null && Math.random() < accuracy) {
            victim.kill();
            return victim;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", accuracy=" + accuracy +
                ", lives=" + lives +
                '}';
    }
}
