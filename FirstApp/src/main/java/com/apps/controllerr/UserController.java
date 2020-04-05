package com.apps.controllerr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.apps.exceptions.UserServiceException;
import com.apps.io.repository.UserRepository;
import com.apps.model.request.UserDetailsRequestModel;
import com.apps.model.response.ErrorMessages;
import com.apps.model.response.OperationStatusModel;
import com.apps.model.response.RequestOperationStatus;
import com.apps.model.response.UserRest;
import com.apps.service.UserServices;

import dto.UserDTO;

@RestController 
@RequestMapping("users")
public class UserController {

	@Autowired
	UserServices userServices;
	
	@Autowired
	UserRepository userRepository;
	
//	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE} )
//	public UserRest getUser(@PathVariable String id) {
//		UserRest returnValue = new UserRest();
//		
//		UserDTO userDTO = userServices.getUserByUserId(id);
//		BeanUtils.copyProperties(userDTO, returnValue);
//		return returnValue;
//	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			     produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserRest returnValue = new UserRest();
		
		if (userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
	
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userDetails, userDTO);
		
		UserDTO createdUser = userServices.createUser(userDTO);
		BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue;
	}
	
	@PutMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE} )
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();
		
		if (userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
	
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userDetails, userDTO);
		
		UserDTO updatedUser = null;//userServices.updateUser(id, userDTO);
		BeanUtils.copyProperties(updatedUser, returnValue);
		
		return returnValue;
	}
	
	@DeleteMapping(path = "/{id}", 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE} )
	public OperationStatusModel deleteUser(@PathVariable String userId, @RequestBody UserDetailsRequestModel userDetails)  {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		//userServices.deleteUser(userId);
		
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<UserRest> returnValue = new ArrayList<>();
		List<UserDTO> users = null;// userServices.getUsers(page, limit);
		
		for (UserDTO userDTO : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDTO, userModel);
			returnValue.add(userModel);
		}
		
		return returnValue;
	}
}
