package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements Runnable {
    public static final int DEFAULT_PORT = 30124;

    private final int port;
    private ServerSocket serverSocket;

    private boolean running;

    public ChatServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        running = true;
        try {
            serverSocket = new ServerSocket(port);

            while (running) {
                System.out.println("Väntar på klient...");
                Socket socket = serverSocket.accept();
                ChatClient client = createClient(socket);
                System.out.println("Någon har anslutit");
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    protected ChatClient createClient(Socket socket) {
        return new ChatClient(null, socket);
    }

    public void close() {
        running = false;
    }
}
