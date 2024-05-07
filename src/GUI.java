package src;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame{
    private JTextField textField; // field is to take input (single line of area)
    private JTextArea messageArea; // area is to display text, a lot of it, from different clients (multiple lines of area)
    private Client client;

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
            public static void actionPerformed(ActionEvent e){ // method invoked when actionlistener class is called
                client.sendMessage(textField.getText()); // take input stream from textfield and send to client
                textField.setText(""); // reset textfield to blank after send(event) is pressed(executed)
            }
        });
        add(textField, BorderLayout.SOUTH); // textfield is at the bottom of the window
    }
}
