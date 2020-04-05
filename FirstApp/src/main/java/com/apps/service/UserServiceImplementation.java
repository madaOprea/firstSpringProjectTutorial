package com.apps.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apps.entity.UserEntity;
import com.apps.exceptions.UserServiceException;
import com.apps.io.repository.UserRepository;
import com.apps.model.response.ErrorMessages;
import com.apps.model.response.UserRest;
import com.apps.shared.Utils;
import com.apps.security.SecurityConstants;

import dto.UserDTO;

@Service
public class UserServiceImplementation implements UserServices {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.print("--- loadUserByUsername ");
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if (userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), 
				userEntity.getEmailVerificationStatus(),
				true, true,
				true, new ArrayList<>());
		
		//return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDTO getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if (userEntity == null) throw new UsernameNotFoundException(email);
		
		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}
	
	@Override
	public UserDTO getUserByUserId(String userID) {
		UserDTO returnValue = new UserDTO();
		UserEntity userEntity = userRepository.findByUserId(userID);
		
		if (userEntity == null) throw new UsernameNotFoundException("User with ID: " + userID + " not found!");
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		UserEntity userEntity = userRepository.findByEmail(userDTO.getEmail());
		if (userEntity != null) throw new RuntimeException("The user already exists!");
		
		//BeanUtils.copyProperties(userEntity, userDTO);
		//BeanUtils.copyProperties(userDTO,  userEntity);	// This is the problem!
		
		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(userDTO, returnValue);
		String publicUserID = utils.generateUserID(30);
		
		returnValue.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		returnValue.setUserId(publicUserID);
		
		UserEntity entityToBeSavedIntoDB = new UserEntity();
		BeanUtils.copyProperties(returnValue, entityToBeSavedIntoDB);
		userRepository.save(entityToBeSavedIntoDB);

		BeanUtils.copyProperties(entityToBeSavedIntoDB, returnValue);
		return returnValue;
	}
	
	@Override
	public UserDTO updateUser(String userId, UserDTO userDTO) {
		UserDTO returnValue = new UserDTO();
		UserEntity userEntity = userRepository.findByEmail(userDTO.getEmail());
		if (userEntity != null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userEntity.setFirstName(userDTO.getFirstName());
		userEntity.setLastName(userDTO.getLastName());
		
		UserEntity updatedUserDetails = userRepository.save(userEntity);
		BeanUtils.copyProperties(updatedUserDetails, returnValue);
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity != null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userRepository.delete(userEntity);		
	}

	@Override
	public List<UserDTO> getUsers(int page, int limit) {
		List<UserDTO> returnValue = new ArrayList<>();
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = userPage.getContent();
		
		for (UserEntity userEntity : users) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
			returnValue.add(userDTO);
		}
		
		return null;
	}
}
