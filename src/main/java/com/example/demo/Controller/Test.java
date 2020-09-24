package com.example.demo.Controller;

import com.example.demo.rjson.RespEntity;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Service
@RequestMapping("/test")
public class Test {
    @ResponseBody
    @RequestMapping(value = "/test")
    public RespEntity test() throws DockerCertificateException, DockerException,
            InterruptedException {
        final DockerClient docker = DefaultDockerClient.builder()
                .uri(URI.create("https://10.251.253.81:2376"))
                .dockerCertificates(new DockerCertificates(Paths.get("./ca")))
                .build();
        final ContainerCreation container = docker.createContainer(ContainerConfig.builder().image("nginx").build());
        final List<Container> containers = docker.listContainers();
        return new RespEntity("success",containers);
    }
}
