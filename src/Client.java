package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class Client {
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private BufferedReader consoleInput = null;

    public Client(String address, int port){
        try{
            socket = new Socket(address, port);
            System.out.println("...........connection established..........");

            consoleInput = new BufferedReader(new InputStreamReader(System.in)); // input from system input stream i.e keyboard
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // take input from server network through socket
            out = new PrintWriter(socket.getOutputStream(), true); // create output stream on server network

            String input = "";
            while(!input.equals("exit")){ // until input from system is not exit i.e keyboard input is not exit
                input = consoleInput.readLine(); // 
                out.println(input); // send input from system to connection stream as output
                System.out.println(in.readLine()); // send input from connection stream as output to system
            }

            out.close();
            in.close();
            consoleInput.close();
        }catch(UnknownHostException e){
            System.out.println("host not found" + e.getMessage());
        }catch(IOException i){
            System.out.println("unknown error " + i.getMessage());
        }
    }

    public static void main(String[] args){
        Client client = new Client("127.0.0.1", 8000);
    }
}
