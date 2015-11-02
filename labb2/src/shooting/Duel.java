package shooting;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Duel {
    private static final Logger LOG = Logger.getLogger(Duel.class.getName());

    private List<Player> players;
    private List<Player> deadPlayers;

    public Duel() {
        players = new ArrayList<>();
        deadPlayers = new ArrayList<>();
    }

    public Player[] getPlayers() {
        return players.toArray(new Player[players.size()]);
    }

    public Player[] getDeadPlayers() {
        return deadPlayers.toArray(new Player[deadPlayers.size()]);
    }

    public void registerPlayers(Player[] players) {
        for (Player player : players) {
            LOG.fine("Added player: " + player.name);
            this.players.add(player);
        }
    }

    private void killPlayer(Player player) {
        if (player.isDead() && !deadPlayers.contains(player)) {
            players.remove(player);
            deadPlayers.add(player);
        }
    }

    public void simulate() {
        boolean running = true;

        while (running) {
            for (int i = 0; i < players.size(); i++) {
                Player killed = players.get(i).shoot();
                if (killed != null)
                    killPlayer(killed);

                if (players.size() <= 1) {
                    running = false;
                    break;
                }
            }
        }
    }

    public Player getWinner() {
        if (countAlive() == 1)
            for (Player player : players)
                if (player.isAlive())
                    return player;

        return null;
    }

    private int countAlive() {
        int count = 0;

        for (Player player : players)
            if (player.isAlive())
                count++;

        return count;
    }
}
