package com.yarnyard.text_proposal_service.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TextProposalRequest {
    private String userId;
    private String storyId;
    private Integer index;
    private String text;
}
