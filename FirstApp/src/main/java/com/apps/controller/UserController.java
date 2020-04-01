package com.apps.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.apps.entity.UserEntity;
import com.apps.exceptions.UserServiceException;
import com.apps.io.repository.UserRepository;
import com.apps.service.UserServices;
import com.response.ErrorMessages;
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
	
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE} )
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();
		
		UserDTO userDTO = userServices.getUserByUserId(id);
		BeanUtils.copyProperties(userDTO, returnValue);
		return returnValue;
	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			     produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		System.out.print("Here 0000000000000000 ");
		UserRest returnValue = new UserRest();
		
		if (userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
	
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userDetails, userDTO);
		System.out.print("Here 11111111111 ");
		UserDTO createdUser = userServices.createUser(userDTO);
		System.out.print("Here 2222222222 ");
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
