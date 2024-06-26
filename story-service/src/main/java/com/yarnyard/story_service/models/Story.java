package com.yarnyard.story_service.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

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
    private String text;
    @Column(name = "main_idea")
    private String mainIdea;
    @Column(name = "user_id")
    private String userId;
}
