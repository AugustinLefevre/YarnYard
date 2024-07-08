package com.yarnyard.text_proposal_service.services;

import com.yarnyard.text_proposal_service.models.TextProposal;
import com.yarnyard.text_proposal_service.repositories.TextProposalRepository;
import com.yarnyard.text_proposal_service.requests.TextProposalRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TextProposalService {

    private final TextProposalRepository repository;

    public TextProposalService(TextProposalRepository repository){
        this.repository = repository;
    }

    public void createNewTextProposal(TextProposalRequest request){
        TextProposal textProposal = new TextProposal();
        BeanUtils.copyProperties(request, textProposal);
        repository.save(textProposal);
    }

    public void deleteTextProposal(Integer textProposalId){
        repository.deleteById(textProposalId);
    }

}
