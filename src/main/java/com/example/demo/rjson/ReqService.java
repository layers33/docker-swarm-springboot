package com.example.demo.rjson;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReqService {

    @JsonProperty(value = "userId")
    private String userId;

    @JsonProperty(value = "image")
    private String image;

    @JsonProperty(value = "replicas")
    private int replicas;

    @JsonProperty(value = "serviceName")
    private String serviceName;

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
