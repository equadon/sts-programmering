package chat;

import chat.packets.LoginPacket;
import chat.packets.UserListPacket;

public interface ChatListener {
    void connected(ChatClient client);
    void disconnected(ChatClient client);

    void loggedIn(ChatClient client, LoginPacket login);

    void messageReceived(String name, String message);

    void userListUpdated(UserListPacket packet);

    void exceptionReceived(Exception exception);
}
