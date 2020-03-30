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
		UserEntity userEntity = userRepository.findByEmail(userDTO.getEmail());
		
		if (userEntity != null) throw new RuntimeException("Attention!");
		
		BeanUtils.copyProperties(userDTO,  userEntity);	
		String publicUserID = utils.generateUserID(30);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		userEntity.setUserId(publicUserID);
		
		UserEntity storedUserDetails = userRepository.save(userEntity);
		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(storedUserDetails, returnValue);
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
