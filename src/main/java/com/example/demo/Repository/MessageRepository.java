package com.example.demo.Repository;

import com.example.demo.Entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message,String> {

    @Override
    public List<Message> findAll();
    public List<Message> findByUserId(String userId);
    public void  deleteMessagesByUserId(String userId);
    public void deleteBy_id(String _id);
}
