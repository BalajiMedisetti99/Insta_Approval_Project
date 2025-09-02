package com.loan_transaction.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SecretProvider {

    private final RestTemplate restTemplate;
    private String cachedSecret;

    public SecretProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getSecret() {
        if (cachedSecret == null) {
            cachedSecret = restTemplate.getForObject(
                "http://localhost:8081/internal/secret",
                String.class
            );
        }
        return cachedSecret;
    }
}


