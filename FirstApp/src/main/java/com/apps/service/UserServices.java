package com.apps.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import dto.UserDTO;

public interface UserServices extends UserDetailsService {
	UserDTO createUser(UserDTO userDTO);
	UserDTO getUser(String email);
	UserDTO getUserByUserId(String userID);	
	UserDTO updateUser(String id, UserDTO userDTO);
}
