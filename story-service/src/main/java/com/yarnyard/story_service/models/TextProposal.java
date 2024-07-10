package com.yarnyard.story_service.models;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class TextProposal {

    private String textProposalId;
    private String userId;
    private String storyId;
    private Integer index;
    private String text;
}
