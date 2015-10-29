public class DiseaseSimulator {
    private static int SIMULATIONS = 100;

    public static double avarage(int n, boolean vaccinated, int width, int height) {
        double dead = 0;

        for (int i = 0; i < n; i++) {
            Village village = new Village(1000, width, height, vaccinated);

            while (village.countSick() > 0)
                village.nextDay();

            dead = dead + village.countDead();
        }

        return dead / n;
    }

    public static void main(String[] args) {
        double avarageDead = avarage(SIMULATIONS, false, 1000, 1000);
        System.out.println("Medelvärdet av antal döda (ej vaccinerade): " + avarageDead);

        avarageDead = avarage(SIMULATIONS, true, 1000, 1000);
        System.out.println("Medelvärdet av antal döda (vaccinerade): " + avarageDead);
    }
}
