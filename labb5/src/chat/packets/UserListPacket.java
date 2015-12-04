package chat.packets;

public class UserListPacket extends ChatPacket {
    private static final long serialVersionUID = 5L;

    public final String[] users;

    public UserListPacket(String[] users) {
        this.users = users;
    }
}
