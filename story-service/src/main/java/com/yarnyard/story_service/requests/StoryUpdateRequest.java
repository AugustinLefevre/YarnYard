package com.yarnyard.story_service.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoryUpdateRequest {
    private String storyId;
    private String title;
    private String maiIdea;
    private String text;
}
