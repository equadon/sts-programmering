package labb4;

import java.awt.*;

public class Molecule extends Ball {
    public static double INIT_INFECTED_PROB = 0.1;
    private static final double INFECTED_PROB = 0.5;
    private static final double DEAD_PROB = 0.005;

    private static final double HEALTH_TIMER_MIN = 2.0; // seconds
    private static final double HEALTH_TIMER_MAX = 4.0; // seconds

    private static final double SECONDS_PER_FRAME = 1d / DiseaseSimulator.UPDATE_FREQUENCY;

    private static final Color HEALTHY_COLOR = Color.WHITE;
    private static final Color INFECTED_COLOR = Color.RED;

    private double healthTimer;
    private float colorIncreasePerFrame;
    private float otherColors;
    private boolean sick;
    private boolean dead;

    private final MoleculesPanel panel;

    public Molecule(MoleculesPanel panel, Coord initialPosition, Coord velocity, double friction, double radius, boolean sick) {
        super(panel.innerBounds, initialPosition, velocity, friction, radius);

        this.panel = panel;
        this.sick = sick;
        dead = false;

        if (sick) {
            becomeSick();
        } else {
            becomeHealthy();
        }
    }

    public boolean isSick() {
        return sick;
    }

    public boolean isDead() {
        return dead;
    }

    public void becomeSick() {
        sick = true;
        healthTimer = HEALTH_TIMER_MIN + Math.random() * (HEALTH_TIMER_MAX - HEALTH_TIMER_MIN);
        color = INFECTED_COLOR;
        colorIncreasePerFrame = (float) (255.0 / (healthTimer / SECONDS_PER_FRAME));
        otherColors = 0;
    }

    public void becomeHealthy() {
        sick = false;
        healthTimer = 0;
        color = HEALTHY_COLOR;
    }

    public void kill() {
        hide();
        sick = false;
        dead = true;
    }

    public void infect(Molecule victim) {
        if (Math.random() < INFECTED_PROB) {
            victim.becomeSick();
        }
    }

    @Override
    protected void collidedWith(Ball ballB) {
        Molecule other = (Molecule) ballB;

        if (!isSick() && other.isSick()) {
            other.infect(this);
        } else if (isSick() && !other.isSick()) {
            infect(other);
        }
    }

    @Override
    void move() {
        super.move();

        if (isSick()) {
            healthTimer -= SECONDS_PER_FRAME;
            otherColors += colorIncreasePerFrame;
            if (otherColors > 255) {
                otherColors = 255;
            }
            color = new Color(255, (int) otherColors, (int) otherColors);

            if (healthTimer <= 0) {
                becomeHealthy();
            }
        }

        if (isSick() && Math.random() < DEAD_PROB) {
            kill();
        }
    }
}
