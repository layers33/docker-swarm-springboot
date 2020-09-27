package com.example.demo.Repository;

import com.example.demo.Entity.Service;
import com.example.demo.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServiceRepository extends MongoRepository<Service, String> {
    @Override
    public List<Service> findAll();
    public Service findByName(String name);
    public Service findByPort(String port);
}
