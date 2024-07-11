package com.yarnyard.chat_service.controllers;

import com.yarnyard.chat_service.entities.Message;
import com.yarnyard.chat_service.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class MessageRestController {
    @Autowired
    private MessageService service;
    @GetMapping("/message")
    public List<Message> getMessages(){
        return service.getMessages();
    }
}
