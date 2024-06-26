package com.yarnyard.user_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yarnyard.user_service.models.User;
public interface UserRepository extends JpaRepository<User, String> {
    public User findByName(String name);
}