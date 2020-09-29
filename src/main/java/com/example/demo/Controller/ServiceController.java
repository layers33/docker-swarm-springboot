package com.example.demo.Controller;


import com.example.demo.Entity.Application;
import com.example.demo.Entity.Message;
import com.example.demo.Repository.ApplicationRepository;
import com.example.demo.Repository.MessageRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserServiceService;
import com.example.demo.rjson.*;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ServiceCreateResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.UnknownHostException;
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
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @ResponseBody
    @RequestMapping(value = "/apply")
    public RespEntity apply(@RequestBody ReqService reqService){
        if(applicationRepository.findByServiceNameAndUserId(reqService.getServiceName(),reqService.getUserId()) != null){
            return new RespEntity("already exist the same name");
        }
        Application application = new Application();
        BeanUtils.copyProperties(reqService,application);
        application.setUsername(userRepository.findBy_id(reqService.getUserId()).getUsername());
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
    public RespEntity agree(@RequestBody ReqService reqService) throws UnknownHostException {
        if(applicationRepository.findByServiceNameAndUserId(reqService.getServiceName(),reqService.getUserId()) == null){
            return new RespEntity("no this application");
        }
        String cmd = userServiceService.getImageCMD(reqService.getImage());
        String targetPort = userServiceService.getImagePort(reqService.getImage());
        String env = userServiceService.getImageEnv(reqService.getImage());
        Random random = new Random();
        int i = random.nextInt(65535) % (55536) + 10000;
        String hostPort = String.valueOf(i);
        while(userServiceService.isPortUsing(i)){
            i = random.nextInt(65535) % (55536) + 10000;
            hostPort = String.valueOf(i);
        }
        ServiceCreateResponse serviceCreateResponse = userServiceService.createServiceTask(reqService.getUserId(),
                cmd,reqService.getImage(),reqService.getReplicas(), reqService.getServiceName(),hostPort,targetPort,env);
        applicationRepository.deleteByServiceNameAndUserId(reqService.getServiceName(),reqService.getUserId());
        if(serviceCreateResponse == null){
            Message message = new Message(reqService.getUserId(),"你请求的service:"+reqService.getServiceName()+"创建失败了");
            messageRepository.save(message);
            return new RespEntity("fail");
        }
        Message message = new Message(reqService.getUserId(),"你请求的service:"+reqService.getServiceName()+"被通过了");
        messageRepository.save(message);
        return new RespEntity("success",serviceCreateResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/refuse")
    public RespEntity refuse(@RequestBody ReqService reqService){
        applicationRepository.deleteByServiceNameAndUserId(reqService.getServiceName(),reqService.getUserId());
        Message message = new Message(reqService.getUserId(),"你请求的service:"+reqService.getServiceName()+"被拒绝了");
        messageRepository.save(message);
        return new RespEntity("success");
    }

    @ResponseBody
    @RequestMapping(value = "/allMyServices")
    public RespEntity allMyServices(@RequestBody ReqUser reqUser){
        List<com.spotify.docker.client.messages.swarm.Service> services = new ArrayList<>();
        String userId = reqUser.getUserId();
        services = userServiceService.listServiceByUserId(userId);
        if(services == null){
            return new RespEntity("fail",services);
        }
        return new RespEntity("success",services);
    }

    @ResponseBody
    @RequestMapping(value = "/allMyMessages")
    public RespEntity allMyMessages(@RequestBody ReqUser reqUser){
        List<Message> messages = new ArrayList<>();
        messages = messageRepository.findByUserId(reqUser.getUserId());
        return new RespEntity("success",messages);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAllMyMessage")
    public RespEntity deleteAllMyMessage(@RequestBody ReqUser reqUser){
        messageRepository.deleteMessagesByUserId(reqUser.getUserId());
        return new RespEntity("success");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteMessage")
    public RespEntity deleteMessage(@RequestBody ReqMessage reqMessage){
        messageRepository.deleteBy_id(reqMessage.getMessageId());
        return new RespEntity("success");
    }

    @ResponseBody
    @RequestMapping(value = "/allServices")
    public RespEntity allServices(){
        List<com.spotify.docker.client.messages.swarm.Service> services = new ArrayList<>();
        services =userServiceService.listAllService();
        List<RespService> respServices = new ArrayList<>();
        if(services == null){
            return new RespEntity("success");
        }
        for(int i = 0; i < services.size(); i++){
            String id = services.get(i).spec().labels().get("userId");
            String username = userRepository.findBy_id(id).getUsername();
            RespService respService = new RespService(username, services.get(i));
            respServices.add(respService);
        }
        return new RespEntity("success",respServices);
    }

    @ResponseBody
    @RequestMapping(value = "/scale")
    public RespEntity scale(@RequestBody ReqService reqService) throws DockerException, InterruptedException {
        userServiceService.scale(reqService.getServiceId(),reqService.getReplicas());
        return new RespEntity("success");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteService")
    public RespEntity deleteService(@RequestBody ReqService reqService){
        userServiceService.removeService(reqService.getServiceId());
        return new RespEntity("success");
    }
}
