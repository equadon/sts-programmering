package chat.packets;

public class ConnectionPacket extends ChatPacket {
    private static final long serialVersionUID = 2L;

    public final String user;
    public final boolean loggedIn;

    public ConnectionPacket(String user, boolean loggedIn) {
        this.user = user;
        this.loggedIn = loggedIn;
    }
}
