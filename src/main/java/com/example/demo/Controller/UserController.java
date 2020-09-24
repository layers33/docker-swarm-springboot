package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.rjson.ReqUser;
import com.example.demo.rjson.RespEntity;
import com.example.demo.rjson.RespUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "*", maxAge = 3600)
@Service
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @RequestMapping(value = "/login")
    public RespEntity login(@RequestBody ReqUser reqUser) {
        User user = userRepository.findUserByUsernameAndPassword(reqUser.getUsername(),reqUser.getPassword());
        if(user == null) {
            return new RespEntity("fail", null);
        }else {
            return new RespEntity("success", user.getUsername());
        }
    }
    @ResponseBody
    @RequestMapping(value = "/register")
    public RespEntity register(@RequestBody ReqUser reqUser) {
        User user = userRepository.findUserByUsername(reqUser.getUsername());
        if(user != null) {
            return new RespEntity("fail", null);
        }else {
            User user1 = new User(reqUser.getUsername(),reqUser.getPassword());
            userRepository.save(user1);
            RespUser respUser = new RespUser();
            BeanUtils.copyProperties(user1,respUser);
            return new RespEntity("success", respUser);
        }
    }
}
