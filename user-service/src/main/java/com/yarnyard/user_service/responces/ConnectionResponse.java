package com.yarnyard.user_service.responces;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ConnectionResponse {
    private String userName;
    private String userId;
    private String token;
}

