package com.example.demo.Controller;


import com.example.demo.Entity.Application;
import com.example.demo.Repository.ApplicationRepository;
import com.example.demo.Repository.ServiceRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserServiceService;
import com.example.demo.rjson.ReqService;
import com.example.demo.rjson.ReqUser;
import com.example.demo.rjson.RespEntity;
import com.spotify.docker.client.messages.ServiceCreateResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "*", maxAge = 3600)
@Service
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private UserServiceService userServiceService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @RequestMapping(value = "/apply")
    public RespEntity apply(@RequestBody ReqService reqService){
        if(applicationRepository.findByServiceNameAndUserId(reqService.getServiceName(),reqService.getUserId()) != null){
            return new RespEntity("already exist the same name");
        }
        Application application = new Application();
        BeanUtils.copyProperties(reqService,application);
        Application result = applicationRepository.save(application);
        return new RespEntity("success",result);
    }

    @ResponseBody
    @RequestMapping(value = "/allApplications")
    public RespEntity allApplications(){
        List<Application> applications = applicationRepository.findAll();
        return new RespEntity("success",applications);
    }

    @ResponseBody
    @RequestMapping(value = "/agree")
    public RespEntity agree(@RequestBody ReqService reqService){
        if(applicationRepository.findByServiceNameAndUserId(reqService.getServiceName(),reqService.getUserId()) == null){
            return new RespEntity("no this application");
        }
        String cmd = userServiceService.getImageCMD(reqService.getImage());
        String targetPort = userServiceService.getImagePort(reqService.getImage());
        String env = userServiceService.getImageEnv(reqService.getImage());
        Random random = new Random();
        int i = random.nextInt(65535) % (55536) + 10000;
        String hostPort = String.valueOf(i);
        while(serviceRepository.findByPort(hostPort) != null){
            i = random.nextInt(65535) % (55536) + 10000;
            hostPort = String.valueOf(i);
        }
        ServiceCreateResponse serviceCreateResponse = userServiceService.createServiceTask(reqService.getUserId(),
                cmd,reqService.getImage(),reqService.getReplicas(), reqService.getServiceName(),hostPort,targetPort,env);
        if(serviceCreateResponse == null){
            return new RespEntity("fail");
        }
        applicationRepository.deleteByServiceNameAndUserId(reqService.getServiceName(),reqService.getUserId());
        return new RespEntity("success",serviceCreateResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/refuse")
    public RespEntity refuse(@RequestBody ReqService reqService){
        applicationRepository.deleteByServiceNameAndUserId(reqService.getServiceName(),reqService.getUserId());
        return new RespEntity("success");
    }

    @ResponseBody
    @RequestMapping(value = "/allMyServices")
    public RespEntity allMyServices(@RequestBody ReqUser reqUser){
        List<com.spotify.docker.client.messages.swarm.Service> services = new ArrayList<>();
        String userId = userRepository.findUserByUsername(reqUser.getUsername()).get_id();
        services = userServiceService.listServiceByUserId(userId);
        if(services == null){
            return new RespEntity("fail",services);
        }
        return new RespEntity("success",services);
    }

}
