
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
            
            // String input = "";
            // while(!input.equals("exit")){ // until input from system is not exit i.e keyboard input is not exit
            //     input = consoleInput.readLine(); // 
            //     out.println(input); // send input from system to connection stream as output
            //     System.out.println(in.readLine()); // send input from connection stream as output to system
            // }

            // out.close();
            // in.close();
            // consoleInput.close();
    }
        
    public void sendMessage(String msg){
        System.out.println("message sent");
            out.println(msg);
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
    }   //catch(UnknownHostException e){
        //     System.out.println("host not found" + e.getMessage());
        // }catch(IOException i){
        //     System.out.println("unknown error " + i.getMessage());
        // }

    // public static void main(String[] args){
    //     Client client = new Client("127.0.0.1", 8000);
    // }
}
