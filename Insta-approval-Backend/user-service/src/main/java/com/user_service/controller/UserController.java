package com.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user_service.dto.AuthRequestDTO;
import com.user_service.dto.AuthResponseDTO;
import com.user_service.dto.RegisterDTO;
import com.user_service.dto.UserDTO;
import com.user_service.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterDTO dto) {
        UserDTO resp = userService.register(dto);
        return ResponseEntity.status(201).body(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO auth) {
        AuthResponseDTO resp = userService.login(auth);
        return ResponseEntity.ok(resp);
    }

    
    @GetMapping("/validate")
    public ResponseEntity<UserDTO> validateToken(@RequestHeader("Authorization") String token) {
        System.out.println("Authorization header = " + token);
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        System.out.println("Actual token = " + actualToken);
        UserDTO dto = userService.validateAndGetUser(actualToken);
        return ResponseEntity.ok(dto);
    }



    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        // you might add findById endpoint; using email-based retrieval is used for validate
        throw new UnsupportedOperationException("Not implemented in this minimal example");
    }
}