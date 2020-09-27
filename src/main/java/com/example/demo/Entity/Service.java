package com.example.demo.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "service")
public class Service {

    private String _id;

    private String userId;

    private String name;

    private String port;

    public Service() {
    }

    public Service(String _id, String userId, String name, String port) {
        this._id = _id;
        this.userId = userId;
        this.name = name;
        this.port = port;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
