package chat.packets;

public class MessagePacket extends ChatPacket {
    private static final long serialVersionUID = 2L;

    public final String user;
    public final String message;

    public MessagePacket(String user, String message) {
        this.user = user;
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessagePacket{" +
                "user='" + user + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
