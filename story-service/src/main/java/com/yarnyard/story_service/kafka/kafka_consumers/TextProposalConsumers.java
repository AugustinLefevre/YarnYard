package com.yarnyard.story_service.kafka_consumers;

import com.yarnyard.story_service.models.TextProposal;
import com.yarnyard.story_service.services.StoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TextProposalConsumers {

    private final StoryService service;

    public TextProposalConsumers(StoryService service){
        this.service = service;
    }

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(TextProposal textProposal) {
        System.err.println("get new text proposal :" + textProposal.toString());
        service.addNewTextProposal(textProposal);
    }

}
