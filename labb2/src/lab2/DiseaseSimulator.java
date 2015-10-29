package lab2;

import javax.swing.*;
import lab2.ui.SimulationUI;

public class DiseaseSimulator {
    private static int SIMULATIONS = 100;

    public static double avarage(int n, boolean vaccinated, int width, int height) {
        double dead = 0;

        for (int i = 0; i < n; i++) {
            Village village = new Village(1000, width, height, vaccinated, Person.INIT_SICK_PROB, Person.GET_WELL_PROB, Person.DEAD_PROB, Person.INFECT_PROB, Person.INFECT_RANGE, Person.DAYS_IMMUNE);

            while (village.countSick() > 0)
                village.nextDay();

            dead = dead + village.countDead();
        }

        return dead / n;
    }

    public static void simulateAvarage() {
        double avarageDead = avarage(SIMULATIONS, false, 1000, 1000);
        System.out.println("Medelvärdet av antal döda (ej vaccinerade): " + avarageDead);

        avarageDead = avarage(SIMULATIONS, true, 1000, 1000);
        System.out.println("Medelvärdet av antal döda (vaccinerade): " + avarageDead);
    }

    public static void main(String[] args) {
        SimulationUI.init();
    }
}
