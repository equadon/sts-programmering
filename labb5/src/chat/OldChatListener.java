package chat;

public interface OldChatListener {
    void connected(ChatClient client);
    void disconnected(ChatClient client);

    void messageReceived(String name, String message);

    void exceptionReceieved(Exception exception);
}
