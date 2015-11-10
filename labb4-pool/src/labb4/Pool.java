package labb4;

import javax.swing.*;

public class Pool {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createFrame();
            }
        });
    }

    private static void createFrame() {
        JFrame frame = new JFrame("Pool");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new TablePanel());
        frame.pack();
        frame.setVisible(true);
    }
}
