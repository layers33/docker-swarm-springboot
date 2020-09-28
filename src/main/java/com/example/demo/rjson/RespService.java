package com.example.demo.rjson;

import com.spotify.docker.client.messages.swarm.Service;

public class RespService {

    private String username;

    private Service service;

    public RespService(String username, Service service) {
        this.username = username;
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
