package com.jessi.pms.models;

/**
 * Created by Jessi on 11/20/2016.
 */

public class Users {
    public String email;
    public String username;
    public String password;
    public String role;

    public Users() {

    }

    public Users(String email, String username, String password, String role) {
        super();

        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
