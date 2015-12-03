package chat;

import javax.swing.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiUserChatServer extends ChatServer implements ChatListener {
    private final JLabel connected;
    private List<ChatClient> clients;

    public MultiUserChatServer(int port, JLabel connected) {
        super(port);

        this.connected = connected;
        clients = new ArrayList<>();
    }

    private void updateConnected() {
        connected.setText("Connected: " + clients.size());
    }

    @Override
    protected ChatClient createClient(Socket socket) {
        return new ChatClient(this, null, socket);
    }

    @Override
    public void connected(ChatClient client) {
        System.out.println("Client connected.");

        if (!clients.contains(client))
            clients.add(client);

        updateConnected();
    }

    @Override
    public void disconnected(ChatClient client) {
        System.out.println("Client disconnected.");

        if (clients.contains(client))
            clients.remove(client);

        updateConnected();
    }

    @Override
    public void messageReceived(String name, String message) {
        System.out.println("Message received: " + message);
    }

    @Override
    public void exceptionReceieved(Exception exception) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat Server");
        JLabel uptime = new JLabel("Uptime: 0");
        JLabel connected = new JLabel("Connected: 0");
        JPanel panel = new JPanel();

        panel.add(uptime);
        panel.add(connected);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        new Thread(new MultiUserChatServer(DEFAULT_PORT, connected)).start();
    }
}
