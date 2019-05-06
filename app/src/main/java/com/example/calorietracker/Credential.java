package com.example.calorietracker;

import java.util.Date;

public class Credential {
    private String username;
    private String passwordHash;
    private Date signUpDate;

    public Credential() {
    }

    public Credential(String username) {
        this.username = username;
    }



    public Credential(String username, String passwordHash, Date signUpDate) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.signUpDate = signUpDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

}

