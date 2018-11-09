/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents the server application, which is a Chat Server multi-threaded
 * @author Khady SARR and Uza LARA-KARGITHESU
 *
 */

public class Server implements Runnable{

	//Variables
    private static ArrayList<UserIdentification> clients;
    private ServerSocket socketserver = null;
    private Socket clientSocket = null;

    public Thread t1;

    //Constructor
    public Server(){
        this.socketserver = testServer.ss;
        this.clients = testServer.clients;
    }

    //Methods
    public void run() {
        boolean done = false;
        try { 
            //Infinite loop to wait for all connections till the server is active
            while(!done){

                // Accept the incoming request from client
                clientSocket = this.socketserver.accept();
                
                //Handle the client if connected (new thread) 
                //This thread (t1) will handle the Client since authentification
                t1 = new Thread(new ClientHandler(clientSocket));
                //Execute the code in the thread
                t1.start();

            }
            
            
             //Stops the server if IOException detected
            
        } catch (IOException e) {
        	
            System.err.println("Error, server");
            try {
				stop();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
    }
    
    public void stop() throws IOException {
        this.socketserver.close();
    }

}

