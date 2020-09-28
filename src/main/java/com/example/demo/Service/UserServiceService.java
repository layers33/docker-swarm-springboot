package com.example.demo.Service;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ServiceCreateResponse;
import com.spotify.docker.client.messages.swarm.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserServiceService {
    public ServiceCreateResponse createServiceTask(String userId, String cmd, String imageId, int replicas,
                                                   String serviceName, String hostPort, String targetPort,String env);

    public String getImagePort(String imageName);

    public String getImageCMD(String imageName);

    public String getImageEnv(String imageName);

    public List<Service> listServiceByUserId(String userId);

    public List<Service> listAllService();

}
