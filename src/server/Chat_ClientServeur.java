package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

//This class will deal the connected user through an infinite loop still the deconnection of the user
public class Chat_ClientServeur implements Runnable {

	//Variables
    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out = null;
    private String login;
    private Thread t3, t4;
    protected static ArrayList<UserIdentification> clients = null;

    //Constructor
    public Chat_ClientServeur(Socket s, String log){

        socket = s;
        login = log;
        this.clients = testServer.clients;
    }

    //Methods
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            clients.add(new UserIdentification(socket,login, in, out));

            Thread t3 = new Thread(new Reception(in,login));
            t3.start();

            Thread t4 = new Thread((Runnable) new Emission());
            t4.start();

        } catch (IOException e) {
            System.err.println(login +"is disconnected ");
        }
    }
}
