package com.yarnyard.text_proposal_service.controllers;

import com.yarnyard.text_proposal_service.requests.TextProposalRequest;
import com.yarnyard.text_proposal_service.services.TextProposalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/text-proposal")
public class TextProposalController {
    TextProposalService service;

    public TextProposalController(TextProposalService service){
        this.service = service;
    }

    @PostMapping
    public void createNewTextProposal(@RequestBody TextProposalRequest request){
        service.createNewTextProposal(request);
    }

    @DeleteMapping("/{id}")
    public void deleteTextProposal(@PathVariable("id") Integer id){
        service.deleteTextProposal(id);
    }
}
