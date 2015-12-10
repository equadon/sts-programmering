package chat;

import chat.packets.LoginPacket;
import chat.packets.UserListPacket;

public interface ChatListener {
    void connected(ChatClient client);
    void disconnected(ChatClient client);
    void disconnected(String user);

    void loggedIn(ChatClient client, LoginPacket login);
    void loggedIn(String user);

    void messageReceived(String name, String message);
    void whisperReceived(String name, String message, String recipient);

    void userListUpdated(UserListPacket packet);

    void exceptionReceived(Exception exception);
}
