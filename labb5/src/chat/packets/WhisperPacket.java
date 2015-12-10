package chat.packets;

public class WhisperPacket extends MessagePacket {
    private static final long serialVersionUID = 6L;

    public final String recipient;

    public WhisperPacket(String user, String message, String recipient) {
        super(user, message);
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "WhisperPacket{" +
                "user='" + user + '\'' +
                "message='" + message + '\'' +
                "recipient='" + recipient + '\'' +
                '}';
    }
}
