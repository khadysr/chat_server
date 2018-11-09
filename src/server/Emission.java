package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

//This class handles the sending of messages by the server to users
public class Emission implements Runnable {

	//Variables
    private Socket socket;
    private PrintWriter out;
    private String message = null;
    private Scanner sc = null;
    protected static ArrayList<UserIdentification> clients = null;
    private SimpleDateFormat sdf;					//To indicated time of received messages

    //Constructor
    public Emission() {
        this.clients = testServer.clients;
    }

    //Methods
    public void run() {

        // To display messages' time hh:mm:ss
        sdf = new SimpleDateFormat("HH:mm:ss");

        String time = sdf.format(new Date());

        sc = new Scanner(System.in);

        while(true){

        	//Display time
            System.out.println("client message at " + time + " :" );
            
            message = sc.nextLine();

            if(clients != null)
            {
            	//To send messages to all clients
                int i = 0;
                for(i=0; i<clients.size(); i++)
                {
                    UserIdentification us = clients.get(i);
                    PrintWriter out = us.getOut();
                    out.println(message);
                    out.flush();
                }
            }
            else
            {
                out.println(message);
                out.flush();
            }
        }
    }
}
