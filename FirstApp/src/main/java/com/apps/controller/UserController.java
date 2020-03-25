package com.apps.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.apps.entity.UserEntity;
import com.apps.repository.UserRepository;
import com.apps.service.UserServices;
import dto.UserDTO;
import model.UserDetailsRequestModel;

@RestController 
@RequestMapping("users")
public class UserController {

	@Autowired
	UserServices userServices;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping
	public String getUser() {
		return "user got!";
	}
	
	@PostMapping
	public UserDTO createUser(@RequestBody UserDetailsRequestModel user) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user,  userEntity);
		userRepository.save(userEntity);
		
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userEntity, userDTO);
		
		UserEntity storedUserDetail = userRepository.save(userEntity);
		UserDTO createdUser = userServices.createUser(userDTO);
		BeanUtils.copyProperties(storedUserDetail, createdUser);
		return userDTO;
	}
	
//	@PostMapping
//	public String createUser() {
//		return "a new user was created!";
//	}
	
	@PutMapping
	public String updateUser() {
		return "an user was deleted!";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "an user was deleted!";
	}
}
