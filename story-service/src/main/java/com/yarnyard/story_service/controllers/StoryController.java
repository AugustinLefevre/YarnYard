package com.yarnyard.story_service.controllers;

import com.yarnyard.story_service.models.Story;
import com.yarnyard.story_service.requests.StoryCreateRequest;
import com.yarnyard.story_service.requests.StoryUpdateRequest;
import com.yarnyard.story_service.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {
    @Autowired
    private StoryService service;

    @GetMapping
    public List<Story> findAll(){return service.getStories();}

    @GetMapping("/user/{id}")
    public List<Story> finAllByUserId(@PathVariable("id") String user_id){
        return service.getAllByUserId(user_id);
    }

    @GetMapping("/{id}")
    public Story findById(@PathVariable("id") String id){
        return service.getStoryById(id);
    }

    @PostMapping
    public String addStory(@RequestBody StoryCreateRequest request){
        Story createdStory = service.addStory(request);
        return createdStory.getStoryId();
    }

    @PutMapping
    public void updateStory(@RequestBody StoryUpdateRequest request){
        service.updateStory(request);
    }

    @DeleteMapping("/{id}")
    public void deleteStory(@PathVariable("id") String id){
        service.deleteStory(id);
    }
}
