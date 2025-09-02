package com.user_service.dto;

import java.time.LocalDate;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private Integer cibilScore;
    private LocalDateTime registrationDate;
    private Set<String> roles;
}