package shooting;

import shooting.strategies.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ShootingSimulator {
    private static final Logger LOG = Logger.getLogger(ShootingSimulator.class.getName());
    public static final int MAX_LIVES = 3;
    private static final int SIMULATIONS = 10000;

    private static Level LOG_LEVEL = Level.ALL;

    public static void main(String[] args) {
        int sofieWins = 0;
        int alvinWins = 0;
        int juliaWins = 0;
        int erikWins = 0;
        int annaWins = 0;

        for (int i = 0; i < SIMULATIONS; i++) {
            LOG.fine("Duel #" + (i + 1));

            Duel duel = new Duel();

            Player[] players = {
                    new Player("Sofie", 0.1, MAX_LIVES, new ChooseNobodyStrategy(duel)),
                    new Player("Alvin", 0.2, MAX_LIVES, new ChooseMostAccurateStrategy(duel)),
                    new Player("Julia", 0.3, MAX_LIVES, new ChooseLeastLivesStrategy(duel)),
                    new Player("Erik", 0.4, MAX_LIVES, new ChooseMostLivesStrategy(duel)),
                    new Player("Anna", 0.5, MAX_LIVES, new ChooseRandomTargetStrategy(duel))
            };

            duel.registerPlayers(players);

            duel.simulate();

            Player winner = duel.getWinner();

            switch (winner.name) {
                case "Sofie":
                    sofieWins++;
                    break;
                case "Alvin":
                    alvinWins++;
                    break;
                case "Julia":
                    juliaWins++;
                    break;
                case "Erik":
                    erikWins++;
                    break;
                case "Anna":
                    annaWins++;
                    break;
            }
        }

        LOG.info(String.format("Sofie: %.1f%%\n", 100 * sofieWins / (double) SIMULATIONS));
        LOG.info(String.format("Alvin: %.1f%%\n", 100 * alvinWins / (double) SIMULATIONS));
        LOG.info(String.format("Julia: %.1f%%\n", 100 * juliaWins / (double) SIMULATIONS));
        LOG.info(String.format("Erik: %.1f%%\n", 100 * erikWins / (double) SIMULATIONS));
        LOG.info(String.format("Anna: %.1f%%\n", 100 * annaWins / (double) SIMULATIONS));
    }
}
