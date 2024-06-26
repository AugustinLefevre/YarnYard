package com.yarnyard.story_service.services;

import com.yarnyard.story_service.models.Story;
import com.yarnyard.story_service.repositories.StoryRepository;
import com.yarnyard.story_service.requests.StoryCreateRequest;
import com.yarnyard.story_service.requests.StoryUpdateRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {
    @Autowired
    private StoryRepository repository;

    public List<Story> getStories(){
        return repository.findAll();
    }

    public Story getStoryById(String id){
        return repository.findById(id).orElse(null);
    }

    public Story addStory(StoryCreateRequest request){
        Story createdStory = new Story();
        BeanUtils.copyProperties(request, createdStory);

        repository.save(createdStory);
        return createdStory;
    }

    public Story updateStory(StoryUpdateRequest request){
        Story updatedStory = new Story();
        BeanUtils.copyProperties(request, updatedStory);

        repository.save(updatedStory);

        return updatedStory;
    }

    public void deleteStory(String id){
        repository.deleteById(id);
    }

    public List<Story> getAllByUserId(String userId)
    {
        return repository.findAllByUserId(userId);
    }
}