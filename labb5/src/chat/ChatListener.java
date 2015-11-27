package chat;

public interface ChatListener {
    void messageReceived(String name, String message);
    void disconnected();

    void exceptionReceieved(Exception exception);
}
