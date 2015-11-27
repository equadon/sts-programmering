package chat;

import java.net.Socket;

public class SingleClientChatServer extends ChatServer {
    private final ChatListener listener;
    private ChatClient client;

    public SingleClientChatServer(int port, ChatListener listener) {
        super(port);

        this.listener = listener;
    }

    @Override
    protected ChatClient createClient(Socket socket) {
        client = new ChatClient(listener, ChatClient.DEFAULT_NAME, socket);
        return client;
    }

    public void send(String message) {
        if (client != null) {
            client.send(message);
        }
    }

    @Override
    public void close() {
        super.close();

        if (client != null) {
            client.close();
        }

        listener.disconnected();
    }
}
