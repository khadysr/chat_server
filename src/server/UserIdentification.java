/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Uza
 */
//An object of this class gathers all information needed to identify an user after authentification
public class UserIdentification {

	//Variables
    private String login;
    private String password;
    private Socket socket;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Map<String, String> userHmap;
    private ArrayList<String> listUsers = new ArrayList();

    //Constructor
    public UserIdentification(Map<String, String> userHmap, String login, String password) {
        this.userHmap = userHmap;
        this.login = login;
        this.password = password;
    }

    public UserIdentification(Socket socket, String login, BufferedReader in, PrintWriter out)
    {
        this.socket = socket;
        this.login = login;
        this.in = in;
        this.out = out;
    }

    //Methods : getters and setters
    
    //get PrintWriter
    public synchronized PrintWriter getOut()
    {
        return out;
    }

    //Get the login
    public synchronized String getLogin() {
        return login;
    }

    //Get the password
    public String getPassword() {
        return password;
    }

    //Get the list of users
    public ArrayList<String> getListUsers() {
        return this.listUsers;
    }

    //Add a new user
    public void addUser(String User) {
        this.listUsers.add(User);
    }

    //Method which return true if the entered password match with the stored credentials.
    public boolean HandleAuthentification() {
        boolean valid = false;
        String checkUser = (String)this.userHmap.get(this.getLogin()); //
        if (checkUser != null && checkUser.equals(this.getPassword())) {//Check in the hashmap the username and password
            System.out.println(this.getLogin() + " access to the server. " );
            valid = true;
        }
        else {
            System.out.print("Bad credentials ");
        }
        return valid;
    }
    
    public boolean verifyLogin(){
        boolean notValid = false;
        if(userHmap.containsKey(this.getLogin())){ //duplicatation of the login
            System.out.println("The login already exists");
            notValid = true;
        }
        return notValid;
    }
}
