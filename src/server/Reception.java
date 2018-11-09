package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//This class is to handle the sending and receiving of messages from the server 
public class Reception implements Runnable {

	//Variables
	private static ArrayList<String> msg = null;
    private static ArrayList<UserIdentification> clients = null;
    private BufferedReader in;
    private String message = null, login = null;
    private String fullMessage = null;
    
    //Constructor
    public Reception(BufferedReader in, String login){

        this.in = in;
        this.login = login;
        this.msg = testServer.msg;
        this.clients = testServer.clients;
    }

    //Methods
    public void run() {
       while(true){
            try {
                //Read message from client
                message = in.readLine();
                
                //Condition format to send to other clients 
                fullMessage = "\n" + login + " : " + message;
                addMsgToTab(msg, fullMessage);
                emission(msg, clients);
                
                if(message.equalsIgnoreCase("LOGOUT")) {
                    System.out.println(this.login + " is disconnected");
                }
                
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    
    //Function to add new messages to array
    public synchronized void addMsgToTab(ArrayList<String> msg, String fullMessage)
    {
    	msg = testServer.msg;
    	msg.add(new String(fullMessage));

    	//Print added message for the server
    	int i=0;
        for(i=0; i<msg.size(); i++)
        {
            System.out.println(msg.get(i) + "\t");
        }
    }
    
    //Function to send messages
    public synchronized void emission(ArrayList<String> msg, ArrayList<UserIdentification> clients)
    {
     
        clients = testServer.clients;
        msg = testServer.msg;
        
        
                if(clients != null)
                {   int i = 0;
                    int j = 0;
                    String text;
                    int l = clients.size();
                    for(i=0; i<(l-1); i++)
                    {
                       UserIdentification us = clients.get(i);
                       PrintWriter out = us.getOut();
                           text = msg.get(0); 
                           out.println(text);
                           out.flush();
                    }    
                }
    }
}
