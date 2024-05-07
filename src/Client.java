
package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.function.Consumer;

public class Client {
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    // private BufferedReader consoleInput = null;
    private Consumer<String> onMessageReceived;

    public Client(String address, int port, Consumer<String> onMessageReceived) throws IOException{
        // try{
            this.socket = new Socket(address, port);
            // System.out.println("...........connection established..........");

            // consoleInput = new BufferedReader(new InputStreamReader(System.in)); // input from system input stream i.e keyboard
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // take input from server network through socket
            this.out = new PrintWriter(socket.getOutputStream(), true); // create output stream on server network
            this.onMessageReceived = onMessageReceived;
    }
        
    public void sendMessage(String msg){
        System.out.println("message sent");
            out.println(msg);
            out.flush();
    }
        
    public void startClient(){
            new Thread(() -> {
                try{
                    String line;
                    while((line=in.readLine()) != null){
                        onMessageReceived.accept(line);
                    }
                }catch(IOException i){
                    i.printStackTrace();
                }
            }).start(); 
    }
}
