package lab2;

public class Village {
    private Person[] population;

    public final int width;
    public final int height;

    public Village(int peopleCount, int width, int height, boolean vaccinated,
            double initSickProb, double getWellProb, double deadProb, double infectProb,
            int infectRange, int daysImmune) {
        this.width = width;
        this.height = height;

        population = new Person[peopleCount];
        for (int i = 0; i < population.length; i++)
            population[i] = new Person(this, vaccinated, initSickProb, getWellProb, deadProb, infectProb, infectRange, daysImmune);
    }

    public Person[] getPopulation() {
        return population;
    }

    public int countSick() {
        int count = 0;
        for (Person person : population)
            if (person.isSick())
                count++;
        return count;
    }

    public int countDead() {
        int count = 0;
        for (Person person : population)
            if (person.isDead())
                count++;
        return count;
    }

    public int countImmune() {
        int count = 0;
        for (Person person : population)
            if (person.isImmune())
                count++;
        return count;
    }

    public void nextDay() {
        for (Person person : population)
            person.nextDay();
    }
}
