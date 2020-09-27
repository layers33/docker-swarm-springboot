package com.example.demo.Service;

import com.spotify.docker.client.messages.ServiceCreateResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserServiceService {
    public ServiceCreateResponse createServiceTask(String userId, String cmd, String imageId, int replicas,
                                                   String serviceName, String hostPort, String targetPort,String env);

    public String getImagePort(String imageName);

    public String getImageCMD(String imageName);

    public String getImageEnv(String imageName);



}
