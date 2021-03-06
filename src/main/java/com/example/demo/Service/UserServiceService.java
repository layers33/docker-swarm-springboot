package com.example.demo.Service;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ServiceCreateResponse;
import com.spotify.docker.client.messages.swarm.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
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

    public boolean isPortUsing(int port) throws UnknownHostException;

    public String scale(String serviceId,Integer num) throws DockerException, InterruptedException;

    public void removeService(String serviceId);
}
