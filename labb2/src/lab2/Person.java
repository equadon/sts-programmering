package lab2;

public class Person {
    private static final double INIT_SICK_PROB = 0.5;
    private static final double GET_WELL_PROB = 0.5;
    private static final double DEAD_PROB = 0.5;
    private static final double INFECT_RANGE = 50;
    private static final int DAYS_IMMUNE = 5;

    private double INFECT_PROB = 0.3;

    private final Village village;

    private boolean dead;
    private boolean sick;

    private double x;
    private double y;

    private int daysLeftImmune;

    public Person(Village village, boolean vaccinated) {
        this.village = village;

        sick = Math.random() < INIT_SICK_PROB;

        x = Math.random() * village.width;
        y = Math.random() * village.height;

        if (vaccinated && Math.random() < 0.5)
            INFECT_PROB = 0.5 * INFECT_PROB;
    }

    public boolean isSick() {
        return !isDead() && sick;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean canBeInfected() {
        return !(isImmune() && isSick());
    }

    public boolean isImmune() {
        return daysLeftImmune > 0;
    }

    public void infect(Person victim) {
        double xDiff = victim.x - x;
        double yDiff = victim.y - y;

        double distance = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));

        if (distance < INFECT_RANGE)
            victim.becomeInfected();
    }

    public void nextDay() {
        if (Math.random() < GET_WELL_PROB)
            sick = false;

        if (isSick()) {
            dead = Math.random() < DEAD_PROB;

            if (!isDead())
                for (Person person : village.getPopulation())
                    if (person != this)
                        infect(person);
        }

        if (daysLeftImmune > 0)
            daysLeftImmune--;
    }

    private void becomeInfected() {
        if (canBeInfected() && Math.random() < INFECT_PROB) {
            sick = true;
            daysLeftImmune = DAYS_IMMUNE;
        }
    }
}
