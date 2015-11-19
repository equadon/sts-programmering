package labb4;

import java.awt.*;

public class Molecule extends Ball {
    private static final double INFECTED_PROB = 0.0;
    private static final double HEALTH_TIMER_START = 2.0; // seconds
    private static final double SECONDS_PER_FRAME = 1d / DiseaseSimulator.UPDATE_FREQUENCY;

    private static final Color HEALTHY_COLOR = Color.WHITE;
    private static final Color INFECTED_COLOR = Color.RED;

    private double healthTimer;
    private boolean sick;

    public Molecule(Rectangle tableBounds, Coord initialPosition, Coord velocity, double friction, double radius, boolean sick) {
        super(tableBounds, initialPosition, velocity, friction, radius);

        this.sick = sick;

        if (sick) {
            becomeSick();
        } else {
            becomeHealthy();
        }
    }

    public boolean isSick() {
        return sick;
    }

    private void becomeSick() {
        sick = true;
        healthTimer = HEALTH_TIMER_START;
        color = INFECTED_COLOR;
    }

    private void becomeHealthy() {
        sick = false;
        healthTimer = 0;
        color = HEALTHY_COLOR;
    }

    public void becomeSick(Molecule victim) {
        if (Math.random() < INFECTED_PROB) {
            victim.becomeSick();
        }
    }

    @Override
    protected void collidedWith(Ball ballB) {
        Molecule other = (Molecule) ballB;

        if (!isSick() && other.isSick()) {
            other.becomeSick(this);
        } else if (isSick() && !other.isSick()) {
            becomeSick(other);
        }
    }

    @Override
    void move() {
        super.move();

        if (isSick()) {
            healthTimer -= SECONDS_PER_FRAME;

            if (healthTimer <= 0) {
                becomeHealthy();
            }
        }
    }
}
