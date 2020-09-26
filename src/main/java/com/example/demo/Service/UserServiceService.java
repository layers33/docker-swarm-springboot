package com.example.demo.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserServiceService {
    public void createServiceTask(String userId, String cmd,String imageId,int replicas,
                                  String serviceName,String hostPort,String targetPort);
}
