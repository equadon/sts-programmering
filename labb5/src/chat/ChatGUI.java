package chat;

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

        dispose();
    }

    private void askIfServer() {
        int answer = JOptionPane.showConfirmDialog(this, "Vill du vara server?");

        try {
            if (answer == 1) {
                setTitle("Chat - Client");
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
            e.printStackTrace();
        }
    }

    private ChatClient createClient() throws IOException {
        String address;
        int port;

        if (SELECT_FAVORITE) {
            String favorite = getFavorite();

            if (favorite == null) {
                address = JOptionPane.showInputDialog(this, "IP Adress:", "130.243.182.97");
                port = Integer.parseInt(JOptionPane.showInputDialog(this, "Port:", ChatServer.DEFAULT_PORT));
            } else {
                String[] values = getFavorite().split(":");
                address = values[1].trim();
                port = Integer.parseInt(values[2]);
            }
        } else {
            address = JOptionPane.showInputDialog(this, "IP Adress:", "130.243.182.97");
            port = Integer.parseInt(JOptionPane.showInputDialog(this, "Port:", ChatServer.DEFAULT_PORT));
        }

        return new ChatClient(this, new Socket(address, port));
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

        if (isServer()) {
            server.send(message);
        } else {
            client.send(message);
        }
    }

    @Override
    public void messageReceived(String message) {
        outputArea.append("Server: " + message + "\n");
    }

    @Override
    public void disconnected() {
        JOptionPane.showMessageDialog(this, "Client disconnected.");
    }

    @Override
    public void exceptionReceieved(Exception exception) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatGUI();
            }
        });
    }
}
