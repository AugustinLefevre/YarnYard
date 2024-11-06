package com.yarnyard.story_service.services;

import com.yarnyard.story_service.models.Story;
import com.yarnyard.story_service.models.TextProposal;
import com.yarnyard.story_service.repositories.StoryRepository;
import com.yarnyard.story_service.requests.StoryCreateRequest;
import com.yarnyard.story_service.requests.StoryUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StoryService {
    private final StoryRepository repository;

    public StoryService(StoryRepository repository)
    {
        this.repository = repository;
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
        createdStory.setCreationDate(new Date());

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

    public List<Story> getPageableStories(int pageIndex){
        Pageable pageable = PageRequest.of(pageIndex, 15, Sort.by("creationDate").descending());
        Page<Story> stories = repository.findAll(pageable);
        if (stories != null) {
            return stories.getContent();
        }
        else{
            throw new EntityNotFoundException("An error occur when trying to access Stories ");
        }
    }
}