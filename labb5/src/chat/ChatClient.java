package chat;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient implements Runnable {
    public static final String DEFAULT_NAME = "Fred";

    public final String name;

    private final OldChatListener listener;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private boolean running;

    public ChatClient(OldChatListener listener, String name, Socket socket) {
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

    public void send(String message) {
        try {
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendObject(final Object object) {
        if (listener != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    listener.messageReceived(name, (String) object);
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
                System.out.println("Got message: " + object);

                sendObject(object);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
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
