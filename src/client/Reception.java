package client;

import java.io.BufferedReader;
import java.io.IOException;

//Once the user authentified, an object of this class will be instiantiated to deal the reception of messages
public class Reception implements Runnable {

	//Variables
    private BufferedReader in;
    private String message = null;

    //Constructor
    public Reception(BufferedReader in2) {
    	this.in = in2;
	}

    //Methods
	public void run() {
        boolean done = false;

        while(!done){
            try {
                message = in.readLine();
                System.out.println(message);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
