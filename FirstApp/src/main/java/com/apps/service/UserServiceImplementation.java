package com.apps.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apps.entity.UserEntity;
import com.apps.repository.UserRepository;
import com.shared.Utils;

import dto.UserDTO;

@Service
public class UserServiceImplementation implements UserServices {

	@Autowired
	UserRepository userRepository;
	
	//@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {

		if (userRepository.findByEmail(userDTO.getEmail()) != null) throw new RuntimeException("qwerty");
		
		UserEntity userEntity = new UserEntity();
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO getUser(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
