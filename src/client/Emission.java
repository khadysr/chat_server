package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//Once the user authentified, an object of this class will be instiantiated to deal the sending messages
public class Emission implements Runnable {

	//Varibles
    private PrintWriter out;
    private String login = null, message = null;
    private Scanner sc = null;

    //Constructor
    public Emission(PrintWriter out2) {
    	this.out = out2;
	}

    //Methods
	public void run() {
        sc = new Scanner(System.in);
        System.out.println("To log out type : LOGOUT  ");
        
        //Infinite loop to get the input from the user
        while(true){
            System.out.println("Write a message ...  ");
            System.out.print("> ");

            //Write a message (we send it to the client)
            message = sc.nextLine();
            out.println(message);
            out.flush();

            if(message.equalsIgnoreCase("LOGOUT")) {
                break;
            }
        }
    }
}
