package com.apps.service;

import java.util.List;

//import org.springframework.security.core.userdetails.UserDetailsService;

import dto.UserDTO;

public interface UserServices {//UserDetailsService {
	UserDTO createUser(UserDTO userDTO);
//	UserDTO getUser(String email);
//	UserDTO getUserByUserId(String userID);	
//	UserDTO updateUser(String id, UserDTO userDTO);
//	void deleteUser(String userId);
//	List<UserDTO> getUsers(int page, int limit);
}
