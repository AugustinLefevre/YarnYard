package com.yarnyard.story_service.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "story")
@Entity
public class Story {
    @Id
    @UuidGenerator
    @Column(name = "story_id", nullable = false)
    private String storyId;
    @Column(name = "title", nullable = false)
    private String title;
    @ElementCollection
    @CollectionTable(
            name = "story_text_proposals", // Table name for text_proposals
            joinColumns = @JoinColumn(name = "story_id") // FK to story table
    )
    @Column(name = "text_proposals", nullable = false)
    private List<String> textProposalIds = new ArrayList<>();
    @Column(name = "main_idea", nullable = false)
    private String mainIdea;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "creation_date", nullable= false)
    private Date creationDate;

    public void addNewTextProposal(TextProposal textProposal){
        this.textProposalIds.add(textProposal.getTextProposalId());
    }
}
