package client;

import java.io.*;
import java.net.*;

//This class is composed of the main to launch clients
public class testClient {

	//Variables
    public static Socket socketClient = null;
    public static Thread t1;

    public static void main(String[] args) {

        try {

            System.out.println("Connection request");
            socketClient = new Socket("127.0.0.1",3005);		//Socket of the client
            System.out.println("Established connection with the server, authentication :");
            
            t1 = new Thread(new Client(socketClient));			//One thread created by client
            t1.start();

        } catch (UnknownHostException e) {
            System.err.println("Cannot connect to address " + socketClient.getLocalAddress());
        } catch (IOException e) {
            System.err.println("No server listening to the port " + socketClient.getLocalPort());
        }

    }

}




