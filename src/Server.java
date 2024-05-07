package src;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import src.Server.ClientHandler;

public class Server{
    private static ArrayList<ClientHandler> clients = new ArrayList<>(); // list to keep track of clients
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000); // setup port on server to listen to incoming client requests
        System.out.println(".........Waiting for connections......."); // indicator of successful server setup

        while(true){
            Socket clientSocket = serverSocket.accept(); // accept connections from clientsockets and create corresponding threads on server
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort());

            // create thread
            /*
             * create object to handle client
             * add client to the list
             * create thread 
            */

             ClientHandler clientThread = new ClientHandler(clientSocket, clients);
             clients.add(clientThread);
             new Thread(clientThread).start(); // start() begins execution of thread; run() method of clienthandler object is invoked (when thread begins)
             System.out.println("connection accepted");
        }
    }

    // runnable interface provides run() necessary for thread execution
    // when thread starts, it invokes the run method of the object which was passed as an argument to the Thread() constructor
    // argument class must implement Runnable interface
    static class ClientHandler implements Runnable{ 
        private Socket clientSocket;
        private ArrayList<ClientHandler> clients = new ArrayList<>();
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException{
            this.clientSocket = clientSocket;
            this.clients = clients;
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        public void run(){
            try{
                String inputLine;
                while((inputLine = in.readLine()) != null){ // read input stream, if not null
                    for(int i=0; i<clients.size(); i++){
                        ClientHandler oneclient = clients.get(i); // one client out of the list
                        oneclient.out.println(inputLine); // print in the output stream of client (PrintWriter)
                    }
                }
            }catch(IOException e){
                System.out.println("error " + e.getMessage());
            } finally{  // closes all the connections with client
                try{
                    in.close(); // close input stream
                    out.close(); // close output stream
                    clientSocket.close(); // close the socket
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}