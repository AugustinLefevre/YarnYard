package com.yarnyard.story_service.services;

import com.yarnyard.story_service.models.Story;
import com.yarnyard.story_service.models.TextProposal;
import com.yarnyard.story_service.repositories.StoryRepository;
import com.yarnyard.story_service.requests.StoryCreateRequest;
import com.yarnyard.story_service.requests.StoryUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StoryService {
    private final StoryRepository repository;

    public StoryService(StoryRepository repository)
    {
        this.repository = repository;
    }

    public List<Story> getStories(){
        return repository.findAll();
    }

    public Story getStoryById(String id){
        return repository.findById(id).orElse(null);
    }

    public Story addStory(StoryCreateRequest request){
        if(request.getTitle() == null || request.getTitle().equals("")){
            throw new RuntimeException("Story title can not be null or empty!");
        }
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

    @Transactional
    public void addNewTextProposal(TextProposal textProposal)
    {
        Optional<Story> storyOptional = repository.findById(textProposal.getStoryId());
        if (storyOptional.isPresent()) {
            Story story = storyOptional.get();
            story.addNewTextProposal(textProposal);
            repository.save(story);
        } else {
            throw new EntityNotFoundException("Story not found with ID: " + textProposal.getStoryId());
        }

    }
}