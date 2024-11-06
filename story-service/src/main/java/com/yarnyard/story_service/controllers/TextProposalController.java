package com.yarnyard.story_service.controllers;

import com.yarnyard.story_service.requests.TextProposalRequest;
import com.yarnyard.story_service.services.TextProposalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/story")
public class TextProposalController {
    TextProposalService service;

    public TextProposalController(TextProposalService service){
        this.service = service;
    }

    @PostMapping("/text-proposal")
    public void createNewTextProposal(@RequestBody TextProposalRequest request){
        service.createNewTextProposal(request);
    }

    @DeleteMapping("/text-proposal/{id}")
    public void deleteTextProposal(@PathVariable("id") Integer id){
        service.deleteTextProposal(id);
    }
}
