package com.intranet.project.classes;

import java.math.BigDecimal;
import java.math.BigInteger;

public class User {
    private BigInteger id;
    private String username;
    private String password;
    private boolean isAdmin = false;
    private String phone;
    private String email;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void updateUser(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
