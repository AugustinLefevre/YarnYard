package com.yarnyard.story_service.repositories;

import com.yarnyard.story_service.models.TextProposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextProposalRepository extends JpaRepository<TextProposal, Integer> {
}
