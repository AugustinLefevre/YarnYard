package com.yarnyard.text_proposal_service.repositories;

import com.yarnyard.text_proposal_service.models.TextProposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextProposalRepository extends JpaRepository<TextProposal, Integer> {
}
