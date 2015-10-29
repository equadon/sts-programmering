package lab2;

public class Person {
    public static final double INIT_SICK_PROB = 0.5;
    public static final double GET_WELL_PROB = 0.5;
    public static final double DEAD_PROB = 0.5;
    public static final double INFECT_PROB = 0.3;
    public static final int INFECT_RANGE = 50;
    public static final int DAYS_IMMUNE = 5;

    private final Village village;

    private boolean dead;
    private boolean sick;

    private double x;
    private double y;

    private int daysLeftImmune;
    
    private final double initSickProb;
    private final double getWellProb;
    private final double deadProb;
    private double infectProb;
    private final int infectRange;
    private final int daysImmune;

    public Person(Village village, boolean vaccinated,
            double initSickProb, double getWellProb, double deadProb, double infectProb,
            int infectRange, int daysImmune) {
        this.village = village;
        
        this.initSickProb = initSickProb;
        this.getWellProb = getWellProb;
        this.deadProb = deadProb;
        this.infectProb = infectProb;
        this.infectRange = infectRange;
        this.daysImmune = daysImmune;

        sick = Math.random() < this.initSickProb;

        x = Math.random() * village.width;
        y = Math.random() * village.height;

        if (vaccinated && Math.random() < 0.5)
            infectProb = 0.5 * infectProb;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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

        if (distance < infectRange)
            victim.becomeInfected();
    }

    public void nextDay() {
        if (Math.random() < getWellProb)
            sick = false;

        if (isSick()) {
            dead = Math.random() < deadProb;

            if (!isDead())
                for (Person person : village.getPopulation())
                    if (person != this)
                        infect(person);
        }

        if (daysLeftImmune > 0)
            daysLeftImmune--;
    }

    private void becomeInfected() {
        if (canBeInfected() && Math.random() < infectProb) {
            sick = true;
            daysLeftImmune = daysImmune;
        }
    }
}
