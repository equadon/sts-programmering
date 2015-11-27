package chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextFieldTest extends JPanel implements ActionListener {
    private final JTextArea area;
    private final JTextField input;
    private final JButton button;

    public TextFieldTest() {
        setPreferredSize(new Dimension(300, 200));

        area = new JTextArea();
        area.setPreferredSize(new Dimension(300, 50));

        input = new JTextField();
        input.setColumns(16);

        button = new JButton("Skicka");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.append(input.getText() + "\n");
                input.setText("");
            }
        });

        add(area);
        add(input);
        add(button);

        input.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        area.append(input.getText() + "\n");
        input.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ChatTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TextFieldTest());
        frame.pack();
        frame.setVisible(true);
    }
}
