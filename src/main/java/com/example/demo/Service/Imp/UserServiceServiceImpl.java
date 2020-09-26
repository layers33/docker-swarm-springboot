package com.example.demo.Service.Imp;

import com.example.demo.Service.UserServiceService;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ServiceCreateResponse;
import com.spotify.docker.client.messages.swarm.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
@Slf4j
public class UserServiceServiceImpl implements UserServiceService {

    private DockerClient dockerSwarmClient = DefaultDockerClient.builder()
            .uri(URI.create("https://10.251.253.81:2376"))
            .dockerCertificates(new DockerCertificates(Paths.get("./ca")))
            .build();

    public UserServiceServiceImpl() throws DockerCertificateException {
    }

    @Override
    public void createServiceTask(String userId, String cmd, String imageName, int replicas,
                                  String serviceName,String hostPort,String targetPort) {
        ServiceSpec.Builder builder = ServiceSpec.builder();
        TaskSpec.Builder taskBuilder = TaskSpec.builder();
        ContainerSpec.Builder containerBuilder = ContainerSpec.builder();
        containerBuilder.image(imageName);
        containerBuilder.tty(true);
        if(!cmd.equals("")) {
            containerBuilder.command(cmd);
        }
        if(!targetPort.equals("") && !hostPort.equals("")){
            PortConfig.Builder portBuilder = PortConfig.builder();
            portBuilder.protocol("tcp");
            int k = Integer.parseInt(targetPort);
            portBuilder.targetPort(k);
            portBuilder.publishedPort(Integer.parseInt(hostPort));
            EndpointSpec endpointSpec = EndpointSpec.builder()
                    .ports(portBuilder.build())
                    .build();

            builder.endpointSpec(endpointSpec);
        }
        taskBuilder.containerSpec(containerBuilder.build());
        builder.taskTemplate(taskBuilder.build());
        builder.name(userId + "-" + serviceName);
        builder.mode(ServiceMode.withReplicas(replicas));
        try{
            ServiceCreateResponse creation = dockerSwarmClient.createService(builder.build());
        }catch (DockerException | InterruptedException requestException){
            log.error("服务创建失败，错误位置：{}，错误原因：{}",
                    "UserServiceServiceImpl.createServiceTask()", requestException.getMessage());
        }

    }
}
