package labb4.game.handlers;

import labb4.game.Table;
import labb4.game.interfaces.GameListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles all game rules.
 */
public abstract class GameHandler {
    private final List<GameListener> listeners;
    private Table table;

    public GameHandler() {
        listeners = new ArrayList<>();
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void addListener(GameListener listener) {
        listeners.add(listener);
    }
    public void removeListener(GameListener listener) {
        listeners.remove(listener);
    }
}
