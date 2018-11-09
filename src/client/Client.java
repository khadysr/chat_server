package client;

/**
 * This class represents the client application, which is a Chat Server multi-threaded
 * @author Khady SARR and Uza LARA-KARGITHESU
 *
 */
import server.User;
import server.UserIdentification;

import javax.swing.*;
import java.net.*;
import java.util.Scanner;
import java.io.*;

//An object of this class is created when an user tries to connect to the server
public class Client implements Runnable {

	//Variables
    private Socket socketClient = null;
    public static Thread thread;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    //Constructor
    public Client(Socket s){

        this.socketClient = s;
    }

    //Methods
    public void run() {

        try {
            String msg = "Client successfully connected to server! " + socketClient.getInetAddress() + ":" + socketClient.getPort();
            System.out.println(msg);
            
            oos = new ObjectOutputStream(socketClient.getOutputStream());   //to write from the socket
            ois = new ObjectInputStream(socketClient.getInputStream());   //to read from the socket
            oos.flush();
            
            //Display possibles choices to user
            System.out.println("1: Create an account ");
            System.out.println("2: Connect to the Chat ");
            System.out.println("3: Exit program");
            String choice;
            choice = JOptionPane.showInputDialog("Enter your choice");
            int number = Integer.parseInt(choice);
            
            switch (number){
                case 1 :
                    //Create an account
                    String newLogin = JOptionPane.showInputDialog("New username");
                    String newPassword = JOptionPane.showInputDialog("New password"); //verifier dans la database si qql a pas déja cette username et le bon format
                    oos.write(1);
                    //Send a request to create an account
                    User newUser = new User(newLogin,newPassword);
                    oos.writeObject(newUser);
                    System.out.println(" " + ois.readUTF());
                    break;

                case 2 :
                    //Authentication if login & password existing
                    String firstLogin = JOptionPane.showInputDialog("Enter your username");
                    String firstPassword = JOptionPane.showInputDialog("Enter the password");
                    oos.write(2);
                    //Send a request to authenticate
                    User firstUser = new User(firstLogin,firstPassword);
                    oos.writeObject(firstUser);
                    boolean validAuthenticate = ois.readBoolean(); //Verify if we put the right login & password in the database
                    if (!validAuthenticate){
                        System.out.println("Information transmitted did not allow you to authenticate.");
                    }
                    else {
                        System.out.printf("*********************************\n");
                        System.out.printf("***     Welcome to the Chat!       ***\n");
                        System.out.printf("*********************************\n\n");
                        thread = new Thread(new Chat_ClientServeur(socketClient));
                        thread.start();
                    }
                    break;

                case 3:
                    System.out.println("Good Bye ! ");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Please select a choice between 1 and 3.");
                    break;
            }

        } catch (IOException e) {

            System.err.println("The server does not respond");
        }
    }

}
