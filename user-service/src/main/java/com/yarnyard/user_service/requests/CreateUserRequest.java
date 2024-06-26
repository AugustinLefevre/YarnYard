package com.yarnyard.user_service.requests;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserRequest {
    private String name;
    private String password;
    private String email;
}
