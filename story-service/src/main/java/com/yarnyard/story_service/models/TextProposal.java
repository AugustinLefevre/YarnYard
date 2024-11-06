package com.yarnyard.text_proposal_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "text_proposal")
@Data
public class TextProposal {
    @Id
    @UuidGenerator
    @Column(name = "text_proposal_id", unique = true)
    private String textProposalId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "story_id")
    private String storyId;
    @Column(name = "text_index", nullable = false)
    private Integer index;
    @Column(nullable = false)
    private String text;
}
