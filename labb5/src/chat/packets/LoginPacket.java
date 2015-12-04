package chat.packets;

public class LoginPacket extends ChatPacket {
    private static final long serialVersionUID = 3L;

    public final String username;
    public final String password;
    public final String failReason;

    public LoginPacket(String user, String message, String failReason) {
        this.username = user;
        this.password = message;
        this.failReason = failReason;
    }

    public boolean isSuccesful() {
        return failReason == null;
    }
}
