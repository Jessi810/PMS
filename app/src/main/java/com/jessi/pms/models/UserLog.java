package com.jessi.pms.models;

import com.google.firebase.database.ServerValue;

/**
 * Created by Jessi on 11/21/2016.
 */

public class UserLog {
    public String id;
    public String role;
    public String username;
    public String date;
    public String time;

    public UserLog() {
    }

    public UserLog(String id, String role, String username) {
        super();

        this.id = id;
        this.role = role;
        this.username = username;
    }

    public UserLog(String id, String role, String username, String date, String time) {
        super();

        this.id = id;
        this.role = role;
        this.username = username;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
