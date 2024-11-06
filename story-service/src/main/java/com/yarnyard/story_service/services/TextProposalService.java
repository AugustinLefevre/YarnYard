package com.yarnyard.text_proposal_service.services;

import com.yarnyard.text_proposal_service.clients.StoryClient;
import com.yarnyard.text_proposal_service.models.TextProposal;
import com.yarnyard.text_proposal_service.repositories.TextProposalRepository;
import com.yarnyard.text_proposal_service.requests.TextProposalRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TextProposalService {

    private final TextProposalRepository repository;
    private final StoryClient storyClient;

    public TextProposalService(TextProposalRepository repository, StoryClient storyClient){
        this.storyClient = storyClient;
        this.repository = repository;
    }

    public void createNewTextProposal(TextProposalRequest request){
        TextProposal textProposal = new TextProposal();
        BeanUtils.copyProperties(request, textProposal);
        repository.save(textProposal);
        storyClient.createNewTextProposal(textProposal);
    }

    public void deleteTextProposal(Integer textProposalId){
        repository.deleteById(textProposalId);
    }

}
