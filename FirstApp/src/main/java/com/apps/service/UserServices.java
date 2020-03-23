package com.apps.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import dto.UserDTO;
import model.User;

public interface UserServices extends UserDetailsService {
	UserDTO createUser(UserDTO DTO);
	UserDTO getUser(String email);
}
