package com.user_service.service.impl;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user_service.constants.ErrorMessages;
import com.user_service.dto.AuthRequestDTO;
import com.user_service.dto.AuthResponseDTO;
import com.user_service.dto.RegisterDTO;
import com.user_service.dto.UserDTO;
import com.user_service.exception.BadRequestException;
import com.user_service.exception.ResourceNotFoundException;
import com.user_service.model.Role;
import com.user_service.model.User;
import com.user_service.repository.RoleRepository;
import com.user_service.repository.UserRepository;
import com.user_service.service.UserService;
import com.user_service.utils.JwtUtil;
import com.user_service.utils.PasswordEncoderUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoderUtil passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapper mapper;

    @Override
    public UserDTO register(RegisterDTO dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        }

        User u = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .dateOfBirth(dto.getDateOfBirth())
                .roles(new HashSet<>())
                .build();

        Role role = roleRepo.findByName("CUSTOMER")
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND + "CUSTOMER"));

        u.getRoles().add(role);
        User saved = userRepo.save(u);

        UserDTO dtoResp = mapper.map(saved, UserDTO.class);
        dtoResp.setRoles(saved.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        return dtoResp;
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO auth) {
        User u = userRepo.findByEmail(auth.getEmail())
                .orElseThrow(() -> new BadRequestException(ErrorMessages.INVALID_EMAIL_PASSWORD));

        if (!passwordEncoder.matches(auth.getPassword(), u.getPassword())) {
            throw new BadRequestException(ErrorMessages.INVALID_EMAIL_PASSWORD);
        }

        
        String token = jwtUtil.generateToken(u.getUserId(), u.getEmail(), u.getRoles());

        return AuthResponseDTO.builder()
                .email(u.getEmail())
                .token(token)
                .build();
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User u = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        UserDTO dto = mapper.map(u, UserDTO.class);
        dto.setRoles(u.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        return dto;
    }

    @Override
    public UserDTO validateAndGetUser(String tokenHeader) {
        String token = normalizeToken(tokenHeader);

        if (!jwtUtil.validateToken(token)) {
            throw new BadRequestException(ErrorMessages.TOKEN_INVALID);
        }

        Long userId = jwtUtil.extractUserId(token);
        if (userId == null) {
            throw new BadRequestException(ErrorMessages.TOKEN_INVALID);
        }

        User u = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        UserDTO dto = mapper.map(u, UserDTO.class);
        dto.setRoles(u.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        return dto;
    }

    private String normalizeToken(String header) {
        if (header == null) throw new BadRequestException(ErrorMessages.TOKEN_INVALID);
        return header.startsWith("Bearer ") ? header.substring(7) : header;
    }
}
