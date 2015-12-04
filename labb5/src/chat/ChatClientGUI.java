package chat;

import chat.packets.LoginPacket;
import chat.packets.MessagePacket;
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

public class ChatClientGUI extends JFrame implements ActionListener, ChatListener {
    private static final boolean SELECT_FAVORITE = true;

    private final JTextField inputArea;
    private final JTextArea outputArea;
    private final JButton sendButton;
    private final JList<String> userList;

    private ChatClient client;

    public ChatClientGUI() {
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

        userList = new JList<>();
        JScrollPane userScroll = new JScrollPane(userList);
        userScroll.setPreferredSize(new Dimension(150, 200));

        JScrollPane outputScroll = new JScrollPane(outputArea);

        outputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(outputScroll, BorderLayout.CENTER);
        add(userScroll, BorderLayout.EAST);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(inputArea, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("Arkiv");
        JMenuItem connectItem = new JMenuItem(new AbstractAction("Anslut") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                connect();
            }
        });
        JMenuItem closeItem = new JMenuItem(new AbstractAction("Stäng") {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeChat();
            }
        });
        fileMenu.add(connectItem);
        fileMenu.add(closeItem);
        menu.add(fileMenu);
        setJMenuBar(menu);

        setPreferredSize(new Dimension(400, 300));

        setTitle("Chat");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void closeChat() {
        client.close();

        System.exit(0);
    }

    private void connect() {
        try {
            outputArea.append("Ansluter till servern...\n");
            client = createClient();
            new Thread(client).start();
        } catch (IOException e) {
            outputArea.append("Kunde inte ansluta.\n");
        }
    }

    private ChatClient createClient() throws IOException {
        String name = null;
        String address = null;
        int port = ChatServer.DEFAULT_PORT;

        if (SELECT_FAVORITE) {
            String favorite = getFavorite();

            if (favorite != null) {
                String[] values = favorite.split(":");
                name = values[0].trim();
                address = values[1].trim();
                port = Integer.parseInt(values[2].trim());
            }
        }

        if (name == null || address == null) {
            name = JOptionPane.showInputDialog(this, "Namn:", "Skapa ny kontakt", JOptionPane.QUESTION_MESSAGE);
            address = JOptionPane.showInputDialog(this, "IP Adress:", "130.243.178.229");
            port = Integer.parseInt(JOptionPane.showInputDialog(this, "Port:", ChatServer.DEFAULT_PORT));

            System.out.println(String.format("%s: %s:%d", name, address, port));
            saveFavorite(String.format("%s: %s:%d", name, address, port));
        }

        ChatClient c = new ChatClient(this, null, new Socket(address, port));
        //ChatClient c = new ChatClient(this, null, new Socket("localhost", ChatServer.DEFAULT_PORT));
        c.setName(name);

        return c;
    }

    private void saveFavorite(String favorite) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Spara kontakt till fil");
        chooser.showDialog(this, "Spara");

        if (chooser.getSelectedFile() != null) {
            FileWriter writer = null;

            try {
                writer = new FileWriter(chooser.getSelectedFile(), true);
                writer.append("\n" + favorite);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage());
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, e.getMessage());
                    }
                }
            }
        }
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
            chooser.setDialogTitle("Välj favorit");
            chooser.showDialog(this, "Välj");

            if (chooser.getSelectedFile() == null) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));
            List<String> lines = new ArrayList();

            String line = reader.readLine();
            while (line != null) {
                if (!line.trim().equals("")) {
                    lines.add(line);
                }

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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        sendMessage();
    }

    private void sendMessage() {
        String message = inputArea.getText();
        outputArea.append("Jag: " + message + "\n");
        inputArea.setText("");

        client.send(new MessagePacket(client.getName(), message));
    }

    @Override
    public void messageReceived(String name, String message) {
        outputArea.append(name + ": " + message + "\n");
    }

    @Override
    public void userListUpdated(UserListPacket packet) {
        userList.setListData(packet.users);
    }

    @Override
    public void connected(ChatClient client) {
        if (this.client == null) {
            this.client = client;
        }

        outputArea.append("Ansluten till servern.\n");

        askLogin();
    }

    private void askLogin() {
        String username = JOptionPane.showInputDialog("Användarnamn:");
        String password = JOptionPane.showInputDialog("Lösenord:");

        client.send(new LoginPacket(username, password, null));
    }

    @Override
    public void disconnected(ChatClient client) {
        outputArea.append("Servern har avslutat.\n");
    }

    @Override
    public void loggedIn(ChatClient client, LoginPacket login) {
        if (login.isSuccesful()) {
            outputArea.append("Inloggad som " + login.username + ".\n");
        } else {
            JOptionPane.showMessageDialog(this, "Inloggning misslyckades: " + login.failReason);

            askLogin();
        }
    }

    @Override
    public void exceptionReceived(Exception exception) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatClientGUI();
            }
        });
    }
}
