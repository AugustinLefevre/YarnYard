package com.yarnyard.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r ->
						r.path("/user/**")
								.uri("lb://user-service")
				)
				.route(r ->
						r.path("/story/**")
								.uri("lb://story-service")
				)
				.route(r ->
						r.path("/text-proposal/**")
								.uri("lb://text-proposal-service")
				)
				.route(r ->
						r.path("/chat/**")
								.uri("lb://chat-service")
				)
				.route(r ->
						r.path("/ai/**")
								.uri("lb://aibot-service")
				)
				.build();
	}
}
