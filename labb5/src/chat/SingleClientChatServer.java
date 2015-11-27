package chat;

import java.net.Socket;

public class SingleClientChatServer extends ChatServer {
    private final ObjectStreamListener listener;
    private ChatClient client;

    public SingleClientChatServer(int port, ObjectStreamListener listener) {
        super(port);

        this.listener = listener;
    }

    @Override
    protected ChatClient createClient(Socket socket) {
        client = new ChatClient(listener, socket);
        return client;
    }

    public void send(String message) {
        if (client != null) {
            client.send(message);
        }
    }
}
