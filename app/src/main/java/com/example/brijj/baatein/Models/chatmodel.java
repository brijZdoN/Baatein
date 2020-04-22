package com.example.brijj.baatein.Models;

public class chatmodel
{
    private String username,text,uid;
    public chatmodel(){}



    public chatmodel(String username, String text, String uid) {
        this.username = username;
        this.text = text;
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getuid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
