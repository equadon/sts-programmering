package chat;

public interface ChatListener {
    void messageReceived(String message);
    void disconnected();

    void exceptionReceieved(Exception exception);
}
