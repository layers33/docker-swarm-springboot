package com.example.demo.Controller;

import com.example.demo.Service.UserServiceService;
import com.example.demo.rjson.RespEntity;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserServiceService userServiceService;

    @ResponseBody
    @RequestMapping(value = "/test")
    public RespEntity test() throws DockerCertificateException, DockerException,
            InterruptedException {
//        final DockerClient docker = DefaultDockerClient.builder()
//                .uri(URI.create("https://10.251.253.81:2376"))
//                .dockerCertificates(new DockerCertificates(Paths.get("./ca")))
//                .build();
//        final ContainerCreation container = docker.createContainer(ContainerConfig.builder().image("nginx").build());
//        final List<Container> containers = docker.listContainers(DockerClient.ListContainersParam.allContainers());
//        final ContainerInfo info = docker.inspectContainer("bd89ca07227535b275df9a1b800d55e59d9593ad88b014b49c2364c652f8307f");
//        final ContainerStats stats = docker.stats("bd89ca07227535b275df9a1b800d55e59d9593ad88b014b49c2364c652f8307f");
//        docker.startContainer("bd89ca07227535b275df9a1b800d55e59d9593ad88b014b49c2364c652f8307f");
//        docker.stopContainer("bd89ca07227535b275df9a1b800d55e59d9593ad88b014b49c2364c652f8307f",1);
//        final List<Container> containers = docker.listContainers();
        ServiceCreateResponse serviceCreateResponse = userServiceService.createServiceTask("111","","nginx",1,"6","8080","80");
        if(serviceCreateResponse == null){
            return new RespEntity("fail");
        }
        return new RespEntity("success",serviceCreateResponse);
    }
}
