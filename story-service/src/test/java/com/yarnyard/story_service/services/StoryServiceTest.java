package com.yarnyard.story_service.services;

import com.yarnyard.story_service.models.Story;
import com.yarnyard.story_service.repositories.StoryRepository;
import com.yarnyard.story_service.requests.StoryCreateRequest;
import com.yarnyard.story_service.requests.StoryUpdateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;

public class StoryServiceTest {

    private final StoryRepository repository;
    private final StoryService service;

    public StoryServiceTest(){
        repository = Mockito.mock(StoryRepository.class);
        service = new StoryService(repository);
    }
    @Test
    public void testGetStories(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("date").descending());
        Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(Mockito.mock(Page.class));
        service.getPageableStories(0);
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
    }
    @Test
    public void testGetStoryById(){
        service.getStoryById("id");
        Mockito.verify(repository, Mockito.times(1)).findById("id");
    }
    @Test
    public void testAddStory(){
        String givenTitle = "title";
        String givenMainIdea = "mainIdea";
        String givenUserId = "id";
        StoryCreateRequest request = StoryCreateRequest.builder()
                .title(givenTitle)
                .mainIdea(givenMainIdea)
                .userId(givenUserId)
                .build();

        var actual = service.addStory(request);
        Assertions.assertTrue(actual instanceof Story);

        Assertions.assertEquals(givenMainIdea, actual.getMainIdea());
        Assertions.assertEquals(givenTitle, actual.getTitle());
        Assertions.assertEquals(givenUserId, actual.getUserId());

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testupdateStory(){
        String givenId = "id";
        String givenMainIdea = "mainIdea";
        String givenText = "text";
        String givenTitle = "title";
        StoryUpdateRequest request = StoryUpdateRequest.builder()
                .storyId(givenId)
                .mainIdea(givenMainIdea)
                .text(givenText)
                .title(givenTitle)
                .build();

        var actual =service.updateStory(request);
        Assertions.assertTrue(actual instanceof Story);

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());

        Assertions.assertEquals(givenId, actual.getStoryId());
        Assertions.assertEquals(givenMainIdea, actual.getMainIdea());
        //Assertions.assertEquals(givenText, actual.getText());
        Assertions.assertEquals(givenTitle, actual.getTitle());
    }

    @Test
    public void testDeleteStory(){
        service.deleteStory("id");
        Mockito.verify(repository, Mockito.times(1)).deleteById("id");
    }

    @Test
    public void testGetAllByUserId(){
        service.getAllByUserId("id");
        Mockito.verify(repository, Mockito.times(1)).findAllByUserId("id");
    }
}
