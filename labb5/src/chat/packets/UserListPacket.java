package chat.packets;

public class UserListPacket extends ChatPacket {
    public final String[] users;

    public UserListPacket(String[] users) {
        this.users = users;
    }
}
