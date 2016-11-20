package com.jessi.pms.models;

/**
 * Created by Jessi on 11/20/2016.
 */

public class EditUser {
    public String fullname;

    public EditUser() {

    }

    public EditUser(String fullname) {
        super();

        this.fullname = fullname;
    }

    public boolean isPasswordValid(String newPassword, String confirmPassword) {
        if (newPassword.equals(confirmPassword)) {
            return true;
        }

        return false;
    }
}
