package chat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ChatGUI extends JFrame implements ActionListener, ObjectStreamListener {
    private final JTextArea inputArea;
    private final JTextArea outputArea;
    private final JButton sendButton;

    private ChatClient client;
    private SingleClientChatServer server;

    public ChatGUI() {
        inputArea = new JTextArea();
        outputArea = new JTextArea();

        sendButton = new JButton("Skicka");
        sendButton.addActionListener(this);

        JScrollPane outputScroll = new JScrollPane(outputArea);
        JScrollPane inputScroll = new JScrollPane(inputArea);

        outputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(outputScroll, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(inputScroll, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        askIfServer();
    }

    public boolean isServer() {
        return server != null;
    }

    private void askIfServer() {
        int answer = JOptionPane.showConfirmDialog(this, "Vill du vara server?");

        try {
            if (answer == 1) {
                client = new ChatClient(this, new Socket("130.243.182.97", ChatServer.DEFAULT_PORT));
                //client = new ChatClient(this, new Socket("localhost", ChatServer.DEFAULT_PORT));
                new Thread(client).start();
            } else if (answer == 0) {
                server = new SingleClientChatServer(ChatServer.DEFAULT_PORT, this);
                new Thread(server).start();
            } else {
                // quit
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String message = inputArea.getText();
        outputArea.append("Jag: " + message + "\n");
        inputArea.setText("");

        if (isServer()) {
            server.send(message);
        } else {
            client.send(message);
        }
    }

    @Override
    public void objectReceived(Object object) {
        outputArea.append("Server: " + object + "\n");
    }

    @Override
    public void exceptionReceieved(Exception exception) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatGUI();
            }
        });
    }
}
