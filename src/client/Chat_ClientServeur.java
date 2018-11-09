package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

//This class is to deal the chat in clients' side
public class Chat_ClientServeur implements Runnable {

	//Variable
    private Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Scanner sc;
    private Thread t3, t4;

    //Constructor
    public Chat_ClientServeur(Socket socketClient) {
    	 this.socket = socketClient;
	}

    //Method
	public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sc = new Scanner(System.in);

            //Launch sending and receiving messages thread in parallel to allow communication without blocking call
            Thread t4 = new Thread(new Emission(out));
            t4.start();
            
            Thread t3 = new Thread(new Reception(in));
            t3.start();


        } catch (IOException e) {
            System.err.println("Remote server has disconnected! ");
        }
    }
}
