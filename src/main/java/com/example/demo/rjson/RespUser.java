package com.example.demo.rjson;

public class RespUser {

    private String _id;

    private String username;

    private String name;

    private String identity;

    public String get_id() {
        return _id;
    }

    public void set_id(String uuid) {
        this._id = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
