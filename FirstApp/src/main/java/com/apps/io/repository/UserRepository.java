package com.apps.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apps.entity.UserEntity;

import model.UserDetailsRequestModel;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
	UserEntity findByEmail(String email);
	
	UserEntity findByUserId(String userID);
}
