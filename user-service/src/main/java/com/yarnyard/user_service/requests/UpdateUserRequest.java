package com.yarnyard.user_service.requests;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserRequest {
    private String user_id;
    private String name;
    private String email;
    private String password;
    private String role;
}
