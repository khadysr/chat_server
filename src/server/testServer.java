package server;

import java.io.IOException;
import java.util.ArrayList;

import java.net.*;

//This class is composed of the main to launch the server
public class testServer {
	
	//Declaration of variables
    public static ArrayList<UserIdentification> clients;				//Array composed of users ID
    public static ArrayList<String> msg;								//Array composed of all messages
    public static ServerSocket ss = null;								//Server's socket
    public static Thread t;												//Thread that initialize the server

    public static void main(String[] args) {

        try {
            //an ArrayList to keep the list of the Client
            clients = new ArrayList<UserIdentification>();
            msg = new ArrayList<String>();
            
            //The ServerSocket instance
            ss = new ServerSocket(3005);
            System.out.println("Server listening on port "+ ss.getLocalPort());

            //This thread will manage all users connections to the server
            t = new Thread(new Server()); 
            t.start();

        } catch (IOException e) {
            System.err.println("Port "+ ss.getLocalPort()+" already use !");
        }

    }


}
