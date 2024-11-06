package com.yarnyard.story_service.services;


import com.yarnyard.story_service.models.TextProposal;
import com.yarnyard.story_service.repositories.TextProposalRepository;
import com.yarnyard.story_service.requests.TextProposalRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TextProposalService {

    private final TextProposalRepository repository;
    private final StoryService storyService;

    public TextProposalService(TextProposalRepository repository, StoryService storyService){
        this.storyService = storyService;
        this.repository = repository;
    }

    public void createNewTextProposal(TextProposalRequest request){
        TextProposal textProposal = new TextProposal();
        BeanUtils.copyProperties(request, textProposal);
        repository.save(textProposal);
        storyService.addNewTextProposal(textProposal);
    }

    public void deleteTextProposal(Integer textProposalId){
        repository.deleteById(textProposalId);
    }

}
