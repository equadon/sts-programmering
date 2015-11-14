package labb4.game.ui;

import labb4.game.Table;
import labb4.game.objects.CueBall;
import labb4.game.objects.PoolBall;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TableContextMenu extends JPopupMenu {
    public TableContextMenu(PoolPanel panel, Table table) {
        super();

        add(new JMenuItem(new AbstractAction("Change turn") {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.changeTurn();
            }
        }));
    }
}
