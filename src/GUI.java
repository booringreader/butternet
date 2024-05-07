package src;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GUI extends JFrame{
    private JTextField textField; // field is to take input (single line of area)
    private JTextArea messageArea; // area is to display text, a lot of it, from different clients (multiple lines of area)
    private Client client;
    private JButton exitButton;

    public GUI(){
        super("ButterNET"); // title of the window
        setSize(500,700);   // size of the window
        setDefaultCloseOperation(EXIT_ON_CLOSE); // what happens when 'x' button is pressed (in windows the app closes, in macOS the window closes)

        // set up restrictions on messageArea
        messageArea = new JTextArea(); 
        messageArea.setEditable(false); // message area should not be editable, textfield should be
        add(new JScrollPane(messageArea), BorderLayout.CENTER); // add messageArea to the scrollpane viewport and keep it in center

        textField = new JTextField();
        textField.addActionListener(new ActionListener(){ // new actionlistener class
            public void actionPerformed(ActionEvent e){ // method invoked when actionlistener class is called
                client.sendMessage(textField.getText()); // take input stream from textfield and send to client
                textField.setText(""); // reset textfield to blank after send(event) is pressed(executed)
            }
        });
        add(textField, BorderLayout.SOUTH); // textfield is at the bottom of the window

        // Styling variables
      Color backgroundColor = new Color(240, 240, 240); // Light gray background
      Color buttonColor = new Color(75, 75, 75); // Darker gray for buttons
      Color textColor = new Color(50, 50, 50); // Almost black for text
      Font textFont = new Font("Arial", Font.PLAIN, 14);
      Font buttonFont = new Font("Arial", Font.BOLD, 12);

      // Apply styles to the message area
      messageArea = new JTextArea();
      messageArea.setEditable(false);
      messageArea.setBackground(backgroundColor);
      messageArea.setForeground(textColor);
      messageArea.setFont(textFont);
      JScrollPane scrollPane = new JScrollPane(messageArea);
      add(scrollPane, BorderLayout.CENTER);


        // Initialize the exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0)); // Exit the application
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);


        try{
            this.client = new Client("127.0.0.1", 8000, this::onMessageReceived);
            client.startClient();
        }catch(IOException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Connecting Server", "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void onMessageReceived(String message){
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n")); //lambda function
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}
