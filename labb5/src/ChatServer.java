import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements Runnable {
    public static final int DEFAULT_PORT = 30123;

    private final ObjectStreamListener listener;
    private final int port;
    private ServerSocket serverSocket;

    public ChatClient testClient;

    private boolean running;

    public ChatServer(ObjectStreamListener listener, int port) {
        this.listener = listener;
        this.port = port;
    }

    @Override
    public void run() {
        running = true;
        try {
            serverSocket = new ServerSocket(port);

            while (running) {
                System.out.println("Väntar på klient...");
                testClient = new ChatClient(listener, serverSocket.accept());
                System.out.println("Någon har anslutit");
                new Thread(testClient).start();
                testClient.send("Hej");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
