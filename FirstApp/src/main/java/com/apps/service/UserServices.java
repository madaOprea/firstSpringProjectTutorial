package com.apps.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import dto.UserDTO;
import model.UserDetailsRequestModel;

public interface UserServices extends UserDetailsService {
	UserDTO createUser(UserDTO userDTO);
	UserDTO getUser(String email);
	UserDTO getUserByUserId(String userID);
}
