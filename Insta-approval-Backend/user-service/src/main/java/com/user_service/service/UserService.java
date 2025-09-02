package com.user_service.service;

import com.user_service.dto.AuthRequestDTO;
import com.user_service.dto.AuthResponseDTO;
import com.user_service.dto.RegisterDTO;
import com.user_service.dto.UserDTO;

public interface UserService {
	
	UserDTO register(RegisterDTO dto);
    AuthResponseDTO login(AuthRequestDTO auth);
    UserDTO getUserByEmail(String email);
    UserDTO validateAndGetUser(String token);

}
