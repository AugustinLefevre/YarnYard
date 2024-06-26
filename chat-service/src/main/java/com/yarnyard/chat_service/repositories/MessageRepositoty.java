package com.yarnyard.chat_service.repositories;

import com.yarnyard.chat_service.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepositoty extends JpaRepository<Message, Integer> {
    Optional<List<Message>> findTop20ByOrderBySentAtDesc();
}
