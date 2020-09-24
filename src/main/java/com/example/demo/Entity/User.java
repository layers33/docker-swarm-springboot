package com.example.demo.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {
    private String _id;

    private String username;

    private String password;

    private String name;

    private String identity;

    public User(String username, String password, String identity) {
        this.username = username;
        this.password = password;
        this.identity = identity;
        this.name = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.name = username;
        this.identity = "0";
    }

    public User() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}