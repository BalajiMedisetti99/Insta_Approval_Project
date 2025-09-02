package com.loan_transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.getCredentials() != null) {
                    String token = auth.getCredentials().toString();
                    template.header("Authorization", "Bearer " + token);
                }
            }
        };
    }
}