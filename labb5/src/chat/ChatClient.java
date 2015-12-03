package chat;

import chat.packets.LoginPacket;
import chat.packets.MessagePacket;
import chat.packets.UserListPacket;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient implements Runnable {
    public static final String DEFAULT_NAME = "Jag";

    private String name;

    private final ChatListener listener;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private boolean running;

    private boolean loggedIn;

    public ChatClient(ChatListener listener, String name, Socket socket) {
        this.listener = listener;
        this.name = name;
        this.socket = socket;

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        listener.connected(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void send(Object object) {
        try {
            output.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendObject(Object object) {
        if (object instanceof MessagePacket) {
            sendMessage((MessagePacket) object);
        } else if (object instanceof LoginPacket) {
            handleLogin((LoginPacket) object);
        } else if (object instanceof UserListPacket) {
            listener.userListUpdated((UserListPacket) object);
        } else {
            System.out.println("Unknown packet: " + object);
        }
    }

    private void handleLogin(LoginPacket login) {
        if (login.isSuccesful()) {
            loggedIn = true;
            name = login.username;
        } else {
            loggedIn = false;
        }

        listener.loggedIn(this, login);
    }

    private void sendMessage(final MessagePacket packet) {
        if (listener != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    listener.messageReceived(packet.user, packet.message);
                }
            });
        }
    }

    @Override
    public void run() {
        running = true;

        try {
            while (running) {
                Object object = input.readObject();
                System.out.println("Got password: " + object);

                sendObject(object);
            }
        } catch (IOException e) {
            //System.out.println("Client disconnected.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (!socket.isClosed()) {
                try {
                    input.close();
                    output.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            listener.disconnected(this);
        }
    }

    public void close() {
        running = false;
    }
}
