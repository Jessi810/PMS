package com.jessi.pms.models;

/**
 * Created by Jessi on 11/20/2016.
 */

public class Users {
    public String email;
    public String username;
    public String fullname;
    public String password;
    public String role;

    public Users() {

    }

    public Users(String email, String username, String fullname, String password, String role) {
        super();

        this.email = email;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
