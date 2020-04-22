package com.example.brijj.baatein.Models;

public class User
{
    private String username,emailid,gender;
    public User()
    { }

    public User(String username, String emailid, String gender)
    {
        this.username = username;
        this.emailid = emailid;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

