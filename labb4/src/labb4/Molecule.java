package labb4;

import java.awt.*;

public class Molecule extends Ball {
    private static final double INFECTED_PROB = 0.1;

    private boolean infected;

    Molecule(Rectangle tableBounds, Coord initialPosition, Coord velocity, double friction, double radius, boolean infected) {
        super(tableBounds, initialPosition, velocity, friction, radius);

        this.infected = infected;

        if (infected) {
            color = Color.RED;
        }
    }

    public boolean isInfected() {
        return infected;
    }

    private void infect() {
        infected = true;
        color = Color.RED;
    }

    public void infect(Molecule victim) {
        if (Math.random() < INFECTED_PROB) {
            victim.infect();
        }
    }

    @Override
    protected void collidedWith(Ball ballB) {
        Molecule other = (Molecule) ballB;

        if (!isInfected() && other.isInfected()) {
            other.infect(this);
        } else if (isInfected() && !other.isInfected()) {
            infect(other);
        }
    }
}
