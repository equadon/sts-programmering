package chat;

import chat.packets.ConnectionPacket;
import chat.packets.LoginPacket;
import chat.packets.MessagePacket;
import chat.packets.UserListPacket;

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

    private String[] getUserNames() {
        String[] names = new String[clients.size()];

        for (int i = 0; i < names.length; i++) {
            names[i] = clients.get(i).getName();
        }

        return names;
    }

    @Override
    protected ChatClient createClient(Socket socket) {
        return new ChatClient(this, null, socket);
    }

    @Override
    public void connected(ChatClient client) {
        System.out.println("Client connected.");

        if (!clients.contains(client)) {
            clients.add(client);
        }

        updateConnected();
    }

    @Override
    public void disconnected(ChatClient client) {
        System.out.println("Client disconnected.");

        if (clients.contains(client)) {
            clients.remove(client);
        }

        for (ChatClient c : clients) {
            if (c != client) {
                c.send(new ConnectionPacket(client.getName(), false));
                c.send(new UserListPacket(getUserNames()));
            }
        }

        updateConnected();
    }

    @Override
    public void disconnected(String user) {}

    @Override
    public void loggedIn(ChatClient client, LoginPacket login) {
        if (login.password.equals("test")) {
            client.send(new LoginPacket(login.username, login.password, null));


            for (ChatClient c : clients) {
                if (c != client) {
                    c.send(new ConnectionPacket(login.username, true));
                }

                c.send(new UserListPacket(getUserNames()));
            }
        } else {
            client.send(new LoginPacket(login.username, login.password, "Incorrect password."));
        }
    }

    @Override
    public void loggedIn(String user) {}

    @Override
    public void messageReceived(String name, String message) {
        for (ChatClient client : clients) {
            if (client.getName() != null && !client.getName().equals(name)) {
                client.send(new MessagePacket(name, message));
            }
        }
    }

    @Override
    public void userListUpdated(UserListPacket packet) {}

    @Override
    public void exceptionReceived(Exception exception) {}

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
