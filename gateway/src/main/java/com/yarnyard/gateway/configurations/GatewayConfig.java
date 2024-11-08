package com.yarnyard.gateway.configurations;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/user/**")
                        .uri("lb://user-service")
                )
                .route(r -> r.path("/story/**")
                        .uri("lb://story-service")
                )
                .route(r -> r.path("/message/**")
                        .uri("lb://chat-service")
                )
                .route(r -> r.path("/chat/**")
                        .uri("lb://chat-service")
                )
                .route(r -> r.path("/current/**")
                        .uri("lb://chat-service")
                )
                .route(r -> r.path("/start/**")
                        .uri("lb://chat-service")
                )
                .route(r -> r.path("/resume/**")
                        .uri("lb://chat-service")
                )
                .route(r -> r.path("/WeatherForecast/**")
                        .uri("lb://chat-service")
                )
                .route(r -> r.path("/ai/**")
                        .uri("lb://aibot-service")
                )
                .build();
    }
}
