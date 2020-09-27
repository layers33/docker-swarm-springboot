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
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ServiceCreateResponse createServiceTask(String userId, String cmd, String imageName, int replicas,
                                  String serviceName,String hostPort,String targetPort,String env) {
        ServiceSpec.Builder builder = ServiceSpec.builder();
        TaskSpec.Builder taskBuilder = TaskSpec.builder();
        ContainerSpec.Builder containerBuilder = ContainerSpec.builder();
        containerBuilder.image(imageName);
        containerBuilder.tty(true);
        // 设置CMD
        if(!cmd.equals("")) {
            containerBuilder.command(cmd);
        }
        // 设置暴露端口
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
        //设置环境变量
        if(!env.equals("")){
            containerBuilder.env(env);
        }
        taskBuilder.containerSpec(containerBuilder.build());
        builder.taskTemplate(taskBuilder.build());
        //设置serviceName
        builder.name(userId + "-" + serviceName);
        // 设置标签
        Map<String,String> labels = new HashMap<String, String>();
        labels.put("userId",userId);
        builder.labels(labels);
        // 设置策略
        builder.mode(ServiceMode.withReplicas(replicas));
        try{
            ServiceCreateResponse creation = dockerSwarmClient.createService(builder.build());
            return creation;
        }catch (DockerException | InterruptedException requestException) {
            log.error("服务创建失败，错误位置：{}，错误原因：{}",
                    "UserServiceServiceImpl.createServiceTask()", requestException.getMessage());
            return null;
        }
    }

    @Override
    public String getImagePort(String imageName){
        String targetPort = "";
        switch (imageName){
            case "nginx":
            case "ubuntu":
                targetPort = "80";
                break;
            case "mysql":
                targetPort = "3306";
                break;
            case "postgres":
                targetPort = "5432";
                break;
            case "redis":
                targetPort = "6379";
                break;
            case "mongo":
                targetPort = "27017";
                break;
            case "tomcat":
                targetPort = "8080";
                break;
        }
        return targetPort;
    }

    @Override
    public String getImageCMD(String imageName){
        String cmd = "";
        switch (imageName){
            case "nginx":
            case "tomcat":
            case "mongo":
            case "mysql":
                cmd = "";
                break;
            case "postgres":
            case "redis":
            case "ubuntu":
                cmd = "/bin/bash";
                break;
        }
        return cmd;
    }

    @Override
    public String getImageEnv(String imageName) {
        String env = "";
        switch (imageName){
            case "nginx":
            case "mongo":
                env = "";
                break;
            case "mysql":
                env = "MYSQL_ROOT_PASSWORD=123456";
                break;
            case "postgres":
            case "redis":
            case "ubuntu":
            case "tomcat":
                break;
        }
        return env;
    }

    @Override
    public List<com.spotify.docker.client.messages.swarm.Service> listServiceByUserId(String userId) {
        List<com.spotify.docker.client.messages.swarm.Service> services = new ArrayList<>();
        com.spotify.docker.client.messages.swarm.Service.Criteria.Builder criteriaBuild =
                com.spotify.docker.client.messages.swarm.Service.Criteria.builder();
        Map<String,String> map = new HashMap<String, String>();
        map.put("userId",userId);
        criteriaBuild.labels(map);
        try{
            services = dockerSwarmClient.listServices(criteriaBuild.build());
            return services;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (DockerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
