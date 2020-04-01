package com.apps.service;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apps.entity.UserEntity;
import com.apps.io.repository.UserRepository;
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
	public UserDTO createUser(UserDTO userDTO) {
		//UserEntity userEntity = userRepository.findByEmail(userDTO.getEmail());
		System.out.print("User Services 00000 ----------");
		//if (userEntity != null) throw new RuntimeException("The user already exists!");
		//BeanUtils.copyProperties(userEntity, userDTO);
		//BeanUtils.copyProperties(userDTO,  userEntity);	// This is the problem!
		
		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(userDTO, returnValue);
		String publicUserID = utils.generateUserID(30);
		System.out.print("User Services ----------");
		returnValue.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		System.out.print("userEntity.getEncrytedPassword() = " + returnValue.getEncryptedPassword());
		returnValue.setUserId(publicUserID);
		System.out.print("publicUserID = " + publicUserID);
		
		UserEntity entityToBeSavedIntoDB = new UserEntity();
		BeanUtils.copyProperties(returnValue, entityToBeSavedIntoDB);
		userRepository.save(entityToBeSavedIntoDB);
		//System.out.print("storedUserDetails" + entityToBeSavedIntoDB.getFirstName());
		//UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(entityToBeSavedIntoDB, returnValue);
		return returnValue;
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
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if (userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDTO getUserByUserId(String userID) {
		UserDTO returnValue = new UserDTO();
		UserEntity userEntity = userRepository.findByUserId(userID);
		
		if (userEntity == null) throw new UsernameNotFoundException(userID);
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}
}
