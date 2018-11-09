package server;

import java.net.*;
import java.util.*;
import java.io.*;

//This class handle the client for the authentification
public class ClientHandler implements Runnable {

	//Variables
    private Socket socketClient;
    public Thread thread;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private static Map<String, String> userHmap = new HashMap<>();
    private FileWriter SaveUsers;
    private static FileReader userFile;
    private boolean isLogged;
    private File file;
    private String path = "Users.txt";

    //Constructor of the class.
    //One instance of this class will run for each client (Multi-threading)    
    public ClientHandler(Socket socketClient) throws IOException {
        this.socketClient = socketClient;
    }

    //Methods
    @Override
    public void run() {
        System.out.println("Thread trying to create Object Input/Output Streams");
        try {

            //Create file if doesn't exists
            this.file = new File("Users.txt");
            if(!file.exists()) {
                file.createNewFile();
            }
            
            //We can write on the file already existing
            else {
                SaveUsers = new FileWriter(file,true);  
            }

            //Load database for user with hash map
            DatabaseUser userDB = new DatabaseUser(userFile, userHmap);
            
            //Create the hash map (login and user) in the data base
            userHmap = userDB.setUserMap();
            
            //Create db
            DatabaseUser saveContentDUser = new DatabaseUser(SaveUsers);
            
            //Creating both Data Stream
            ObjectOutputStream oos = new ObjectOutputStream(socketClient.getOutputStream()); //Send messages to clientt
            ObjectInputStream ois = new ObjectInputStream(socketClient.getInputStream());//Read messages of client
            oos.flush();

            //Choice sending by the client
            int choice = ois.read();
            
            //Catch all the requests of the client
            switch (choice) {
                case 1:
                    //Read the username & password (objet User) of New account
                    User newUser = (User) ois.readObject();
                    System.out.println(newUser.getLogin() + " is requesting to create a new account.");
                    System.out.println(newUser.getPassword() + " as a password");
                    
                    // Load in the database the user
                    String saveUser = newUser.getLogin() + "," + newUser.getPassword();
                    
                    //Verify if the login already exist in the database
                    UserIdentification verifyAccountObject = new UserIdentification(userHmap,newUser.getLogin(),newUser.getPassword());
                    if(verifyAccountObject.verifyLogin()){
                        oos.writeUTF(" Login already existing.");
                        oos.flush();
                    }
                    else {
                        saveContentDUser.SaveUserFile(saveUser);//adding the user in the database
                        oos.writeUTF("New account is created.");
                        oos.flush();
                    }
                    break;
                    
                case 2:
                    //Authenticate a user
                    User firstUser = (User) ois.readObject();
                    oos.flush();
                    UserIdentification authenticationObject = new UserIdentification(userHmap,firstUser.getLogin(),firstUser.getPassword());
                    if(authenticationObject.HandleAuthentification()) { //true
                        authenticationObject.addUser(firstUser.getLogin()); //adding user
                        oos.writeBoolean(true);
                        this.isLogged = true;
                        oos.flush();
                        thread = new Thread(new Chat_ClientServeur(socketClient,firstUser.getLogin()));
                        thread.start();
                    }
                    else {
                        oos.writeBoolean(false);
                        oos.flush();
                        this.isLogged = false;
                    }
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);//exit the application
        }
    }
}
