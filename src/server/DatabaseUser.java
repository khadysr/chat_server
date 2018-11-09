package server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Database for users
 */

public class DatabaseUser {

    private FileReader listUsers;
    private FileWriter saveUser;
    private BufferedReader buffer;
    private Map<String, String> userHmap; //Hashmap(Key = login -> ,Value = password)
    private File file;

    // to load users
    public DatabaseUser(FileReader listUsers, Map<String, String> userHmap) throws FileNotFoundException {
        this.listUsers= new FileReader("Users.txt");
        this.userHmap = new HashMap<>();
    }

    // Keep the existing content and append the new user to the end of a file.
    public DatabaseUser(FileWriter saveUser) throws IOException {
        this.saveUser = new FileWriter("Users.txt", true);
    }

    //Create the hash map : login and user (two table)
    public Map setUserMap() throws IOException {
        BufferedReader buffer = new BufferedReader(this.listUsers); // Create a buffer from listUsers

        for(String line = buffer.readLine(); line != null; line = buffer.readLine()) { //Read the line for login & password
            String[] table = line.split(",");//split the line for login & password
            this.userHmap.put(table[0], table[1]);//put login & pass on the hashmap (key,value)
        }
        buffer.close(); //close the buffer
        this.listUsers.close();
        return this.userHmap;
    }
    //Saving the user file
    public void SaveUserFile(String textline) throws IOException {
        this.saveUser.write(textline);
        this.saveUser.write("\r\n");
        this.saveUser.close();
    }
}
