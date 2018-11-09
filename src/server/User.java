package server;

import java.io.Serializable;

//This class is to collect the login and password of the connecting user for authentification
public class User implements Serializable {
    private String login;
    private String password;


    public User(String newLogin, String newPassword) {
        this.login = newLogin;
        this.password = newPassword;
	}

	public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
