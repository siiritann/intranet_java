package com.intranet.project.classes;

import java.math.BigInteger;
import java.util.Date;

public class UserCreation {
    // User Response class for User creation
    private String username;
    private String password;
    // isAdmin is by default false, each user can be set to admin by the first admin
    private boolean isAdmin = false;
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
