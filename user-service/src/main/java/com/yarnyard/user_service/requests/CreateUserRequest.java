package com.yarnyard.user_service.requests;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String name;
    private String password;
    private String email;
}
