package com.loan_transaction.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer cibilScore;
    private Set<String> roles;
}