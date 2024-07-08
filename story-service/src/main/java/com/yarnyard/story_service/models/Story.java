package com.yarnyard.story_service.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

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
    @Column(name = "story_id")
    private String storyId;
    private String title;
    @Column(name = "text_proposals")
    private List<Integer> textProposals;
    @Column(name = "main_idea")
    private String mainIdea;
    @Column(name = "user_id")
    private String userId;
}
