package com.example.demo.Repository;

import com.example.demo.Entity.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application,String> {

    @Override
    public List<Application> findAll();
    public Application findByServiceNameAndUserId(String name,String userId);
    public void deleteByServiceNameAndUserId(String name,String userId);
}
