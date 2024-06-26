package com.yarnyard.story_service.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class StoryCreateRequest {
    private String title;
    private String mainIdea;
    private String userId;
}
