package com.example.doanfood;

import java.sql.Blob;

public class User {
    int id;
    String username;
    String password;
    String email;
    Integer contact;
    String address;
    byte[] image;
    public User(){}

    public User(int id,String username, String password, String email, Integer contact, String address, byte[] image) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Integer getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public byte[] getImage() {
        return image;
    }




}
