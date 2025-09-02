package com.user_service.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;       // this is customerId in your earlier design

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // bcrypt

    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private Integer cibilScore;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    @PrePersist
    private void prePersist() {
        if (this.registrationDate == null) this.registrationDate = LocalDateTime.now();
        if (this.cibilScore == null) this.cibilScore = 700; // default
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}



 
