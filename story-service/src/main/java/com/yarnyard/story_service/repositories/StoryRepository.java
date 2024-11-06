package com.yarnyard.story_service.repositories;

import com.yarnyard.story_service.models.Story;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, String> {
    public List<Story> findAllByUserId(String userId);
}
