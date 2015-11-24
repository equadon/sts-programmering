import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements Runnable {
    public static final int DEFAULT_PORT = 30123;

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
                ChatClient client = new ChatClient(null, serverSocket.accept());
                System.out.println("Någon har anslutit");
                new Thread(client).start();
                client.send("Hej");
                System.out.println("Skickade 'Hej'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
