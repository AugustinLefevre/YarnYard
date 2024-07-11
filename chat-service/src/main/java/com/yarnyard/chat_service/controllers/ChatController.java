package com.yarnyard.chat_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yarnyard.chat_service.entities.Message;
import com.yarnyard.chat_service.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller("/chat")
public class ChatController {
    @Autowired
    MessageService service;
    @MessageMapping("/resume")
    @SendTo("/start/initial")
    public String chat(String msg) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Message message = objectMapper.readValue(msg, Message.class);
        service.addMessage(message);
        return msg;
    }
}

