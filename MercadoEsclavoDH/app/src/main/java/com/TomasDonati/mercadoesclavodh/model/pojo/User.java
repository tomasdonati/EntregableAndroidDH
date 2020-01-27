package com.TomasDonati.mercadoesclavodh.model.pojo;

public class User {

    private String userFullName;
    private String userEmail;

    public User() {
    }

    public User(String userEmail, String userFullName) {
        this.userEmail = userEmail;
        this.userFullName = userFullName;
    }


    public String getUserFullName(){
        return userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
