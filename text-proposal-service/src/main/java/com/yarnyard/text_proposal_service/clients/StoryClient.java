package com.yarnyard.text_proposal_service.clients;

import com.yarnyard.text_proposal_service.models.TextProposal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StoryClient {

    private final KafkaTemplate<String, TextProposal> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public StoryClient(KafkaTemplate<String, TextProposal> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    public void createNewTextProposal(TextProposal textProposal){
        kafkaTemplate.send(topicName, textProposal.getTextProposalId(), textProposal);
    }
}
