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

public class ChatClientGUI extends JFrame implements ActionListener, ChatListener {
    private static final boolean SELECT_FAVORITE = true;

    private final JTextField inputArea;
    private final JTextArea outputArea;
    private final JButton sendButton;

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
        String address;
        int port;

/*        if (SELECT_FAVORITE) {
            String favorite = getFavorite();

            if (favorite == null) {
                address = JOptionPane.showInputDialog(this, "IP Adress:", "130.243.178.229");
                port = Integer.parseInt(JOptionPane.showInputDialog(this, "Port:", ChatServer.DEFAULT_PORT));
            } else {
                String[] values = favorite.split(":");
                address = values[1].trim();
                port = Integer.parseInt(values[2].trim());
            }
        } else {
            address = JOptionPane.showInputDialog(this, "IP Adress:", "130.243.178.229");
            port = Integer.parseInt(JOptionPane.showInputDialog(this, "Port:", ChatServer.DEFAULT_PORT));
        }

        return new ChatClient(this, null, new Socket(address, port));*/
        return new ChatClient(this, null, new Socket("localhost", ChatServer.DEFAULT_PORT));
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
    public void connected(ChatClient client) {
        if (this.client == null) {
            this.client = client;
        }

        outputArea.append("Ansluten till servern.");
    }

    @Override
    public void disconnected(ChatClient client) {
        outputArea.append("Servern har avslutat.\n");
    }

    @Override
    public void exceptionReceieved(Exception exception) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatClientGUI();
            }
        });
    }
}
