package chat;

public interface ChatListener extends OldChatListener {
    void connected(ChatClient client);
    void disconnected(ChatClient client);

    void messageReceived(String name, String message);

    void exceptionReceieved(Exception exception);
}
