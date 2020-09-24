package com.example.demo.Repository;

import com.example.demo.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    @Override
    public List<User> findAll();
    public User findBy_id(String uuid);
    public User findBy_idAndPassword(String uuid, String password);
    public User findUserByUsername(String username);
    public User findUserByUsernameAndPassword(String username,String password);
}
