public class ConsoleChat {
    public ConsoleChat() {
        ChatServer server = new ChatServer(null, ChatServer.DEFAULT_PORT);
        new Thread(server).start();
    }

    public static void main(String[] args) {
        new ConsoleChat();
    }
}
