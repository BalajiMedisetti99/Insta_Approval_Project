package com.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GatewayConfig {

    
    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
               
                .route("user-service", r -> r.path("/api/v1/customers/**")
                        .uri("http://localhost:8081"))
               
                .route("loan-service", r -> r.path("/loans/**", "/admin/**")
                        .uri("http://localhost:8082"))
                .build();
    }

    
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        
                        .pathMatchers("/api/v1/customers/register", "/api/v1/customers/login").permitAll()
                        
                        .anyExchange().permitAll()
                )
                .build();
    }
}
