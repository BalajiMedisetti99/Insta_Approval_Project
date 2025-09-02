package com.user_service.dto;


import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class RegisterDTO {
    @NotBlank 
    private String name;
    @NotBlank 
    @Email 
    private String email;
    @NotBlank 
    private String password;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
}
