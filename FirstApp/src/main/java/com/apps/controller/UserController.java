package com.apps.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.apps.entity.UserEntity;
import com.apps.io.repository.UserRepository;
import com.apps.service.UserServices;
import com.response.UserRest;

import dto.UserDTO;
import model.UserDetailsRequestModel;

@RestController 
@RequestMapping("users")
public class UserController {

	@Autowired
	UserServices userServices;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(path = "/{id}")
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();
		
		UserDTO userDTO = userServices.getUserByUserId(id);
		BeanUtils.copyProperties(userDTO, returnValue);
		return returnValue;
	}
	
	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();
	
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userDetails, userDTO);

		UserDTO createdUser = userServices.createUser(userDTO);
		BeanUtils.copyProperties(createdUser, returnValue);
		return returnValue;
	}
	
	@PutMapping
	public String updateUser() {
		return "an user was deleted!";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "an user was deleted!";
	}
}
