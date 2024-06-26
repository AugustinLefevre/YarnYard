package com.yarnyard.story_service.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class StoryUpdateRequest {
    private String storyId;
    private String title;
    private String mainIdea;
    private String text;
}
