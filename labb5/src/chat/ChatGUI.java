package chat;

import chat.packets.LoginPacket;
import chat.packets.UserListPacket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatGUI extends JFrame implements ActionListener, ChatListener {
    private static final boolean SELECT_FAVORITE = true;

    private final JTextField inputArea;
    private final JTextArea outputArea;
    private final JButton sendButton;

    private ChatClient client;
    private SingleClientChatServer server;

    public ChatGUI() {
        inputArea = new JTextField();
        inputArea.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sendMessage();
            }
        });

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        sendButton = new JButton("Skicka");
        sendButton.addActionListener(this);

        JScrollPane outputScroll = new JScrollPane(outputArea);

        outputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(outputScroll, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(inputArea, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("Arkiv");
        JMenuItem closeItem = new JMenuItem(new AbstractAction("Stäng") {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeChat();
            }
        });
        fileMenu.add(closeItem);
        menu.add(fileMenu);
        setJMenuBar(menu);

        setPreferredSize(new Dimension(400, 300));

        setTitle("Chat");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        askIfServer();
    }

    public boolean isServer() {
        return server != null;
    }

    private void closeChat() {
        if (isServer()) {
            server.close();
        } else {
            client.close();
        }

        System.exit(0);
    }

    private void askIfServer() {
        String[] options = {"Server", "Klient"};
        int answer = JOptionPane.showOptionDialog(this, "Välj klient eller server?", "Välj typ", 0, JOptionPane.QUESTION_MESSAGE, null, options, null);

        try {
            if (answer == 1) {
                setTitle("Chat - Client");
                outputArea.append("Ansluter till servern...\n");
                client = createClient();
                new Thread(client).start();
            } else if (answer == 0) {
                setTitle("Chat - Server");
                server = createServer();
                new Thread(server).start();
            } else {
                // quit
            }
        } catch (IOException e) {
            outputArea.append("Kunde inte ansluta.\n");
        }
    }

    private ChatClient createClient() throws IOException {
        String name = "Fred";
        String address;
        int port;

        if (SELECT_FAVORITE) {
            String favorite = getFavorite();

            if (favorite == null) {
                address = JOptionPane.showInputDialog(this, "IP Adress:", "130.243.178.229");
                port = Integer.parseInt(JOptionPane.showInputDialog(this, "Port:", ChatServer.DEFAULT_PORT));
            } else {
                String[] values = favorite.split(":");
                name = values[0].trim();
                address = values[1].trim();
                port = Integer.parseInt(values[2].trim());
            }
        } else {
            address = JOptionPane.showInputDialog(this, "IP Adress:", "130.243.178.229");
            port = Integer.parseInt(JOptionPane.showInputDialog(this, "Port:", ChatServer.DEFAULT_PORT));
        }

        return new ChatClient(this, name, new Socket(address, port));
    }

    private String getFavorite() {
        List<String> favoriteList = readFavorites();
        if (favoriteList == null) {
            return null;
        }

        String[] favorites = favoriteList.toArray(new String[favoriteList.size()]);

        return (String) JOptionPane.showInputDialog(this, "Välj favorit", "Favoriter", JOptionPane.QUESTION_MESSAGE, null, favorites, favorites[0]);
    }

    private List<String> readFavorites() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.showDialog(this, "Välj favoriter");

            if (chooser.getSelectedFile() == null) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));
            List<String> lines = new ArrayList();

            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();

            return lines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private SingleClientChatServer createServer() throws IOException {
        int port = Integer.parseInt(JOptionPane.showInputDialog(this, "Port:", ChatServer.DEFAULT_PORT));

        return new SingleClientChatServer(port, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        sendMessage();
    }

    private void sendMessage() {
        String message = inputArea.getText();
        outputArea.append("Jag: " + message + "\n");
        inputArea.setText("");

        client.send(message);
    }

    @Override
    public void messageReceived(String name, String message) {
        outputArea.append(name + ": " + message + "\n");
    }

    @Override
    public void userListUpdated(UserListPacket packet) {}

    @Override
    public void connected(ChatClient client) {
        if (this.client == null) {
            this.client = client;
        }

        if (isServer()) {
            outputArea.append("En klient har anslutit.\n");
        } else {
            outputArea.append("Ansluten till servern.");
        }
    }

    @Override
    public void disconnected(ChatClient client) {
        if (isServer()) {
            outputArea.append("En klient har lämnat.\n");
        } else {
            outputArea.append("Servern har avslutat.\n");
        }
    }

    @Override
    public void loggedIn(ChatClient client, LoginPacket packet) {}

    @Override
    public void exceptionReceived(Exception exception) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatGUI();
            }
        });
    }
}
