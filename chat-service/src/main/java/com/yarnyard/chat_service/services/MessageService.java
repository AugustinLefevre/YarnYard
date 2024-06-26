package com.yarnyard.chat_service.services;

import com.yarnyard.chat_service.entities.Message;
import com.yarnyard.chat_service.repositories.MessageRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepositoty repositoty;

    public void addMessage(Message message){
        repositoty.save(message);
    }

    public List<Message> getMessages(){
        List<Message> result = repositoty.findTop20ByOrderBySentAtDesc().orElse(null);
        Collections.reverse(result);
        return result;
    }
}
