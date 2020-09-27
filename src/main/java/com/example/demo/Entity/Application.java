package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "application")
public class Application {

    private String _id;

    private String userId;

    private String image;

    private int replicas;

    private String serviceName;

    public Application() {
    }

    public Application(String userId, String image, int replicas, String serviceName) {
        this.userId = userId;
        this.image = image;
        this.replicas = replicas;
        this.serviceName = serviceName;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
