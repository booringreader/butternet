package src;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;

public class GUI extends JFrame {
    private JTextField textField; // field is to take input (single line of area)
    private JTextArea messageArea; // area is to display text, a lot of it, from different clients (multiple lines of area)
    private Client client;
    private JButton exitButton;

    public GUI() {
        super("ButterNET"); // title of the window
        setSize(390, 550); // size of the window
        setDefaultCloseOperation(EXIT_ON_CLOSE); // what happens when 'x' button is pressed (in windows the app closes, in macOS the window closes)
        // ImageIcon image = new ImageIcon("cp.png");
        // setIconImage(image.getImage()); // icon on the title bar

        // custom name prompt
        String name = JOptionPane.showInputDialog(this, "NAME: ", "NAME Entry", JOptionPane.PLAIN_MESSAGE);
        this.setTitle("BUTTERNET-" + name);

        // Styling variables
        Color backgroundColor = new Color(220, 220, 220); // Light gray background
        Color buttonColor = new Color(75, 75, 75); // Darker gray for buttons
        Color textColor = new Color(0x000000); 
        Font textFont = new Font("Georgia", Font.PLAIN, 18);
        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        // design messageArea
        messageArea = new JTextArea();
        messageArea.setEditable(false); // message area should not be editable, textfield should be
        add(new JScrollPane(messageArea), BorderLayout.CENTER); // add messageArea to the scrollpane viewport and keep it in center

        // Apply styles to the MESSAGE_AREA
        messageArea.setEditable(false);
        messageArea.setBackground(backgroundColor);
        messageArea.setForeground(textColor);
        messageArea.setFont(textFont);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.CENTER);


        // Apply styles to the TEXT_AREA
        textField = new JTextField();
        textField.setBackground(backgroundColor);
        textField.setForeground(textColor);
        textField.setFont(textFont);
        textField.setSize(200, 30);
        // design TEXT_AREA
        textField.addActionListener(new ActionListener() { // new actionlistener class
            public void actionPerformed(ActionEvent e) { // method invoked when actionlistener class is called
                String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + name + ": "
                        + textField.getText();
                client.sendMessage(message); // take input stream from textfield and send to client
                textField.setText(""); // reset textfield to blank after send(event) is pressed(executed)
            }
        });
        add(textField, BorderLayout.SOUTH); // textfield is at the bottom of the window

        // design and style the EXIT_BUTTON
        exitButton = new JButton("Exit");
        exitButton.setForeground(Color.black);
        exitButton.setBackground(backgroundColor);
        exitButton.setFont(buttonFont);
        exitButton.setOpaque(true);
        exitButton.setContentAreaFilled(false);

        exitButton.addActionListener(e -> {
            String departureMESSAGE = "sadly " + name + " has left the chat 😔";
            client.sendMessage(departureMESSAGE);
            // window closing delay
            try{
                Thread.sleep(850);
            }catch(InterruptedException i){
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        }); // Exit the application

        // bottompanel to hold text box and exit button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        try {
            this.client = new Client("127.0.0.1", 8000, this::onMessageReceived);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Connecting Server", "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void onMessageReceived(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n")); // lambda function
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}
