package com.cognizant.authorizationservice.service;

import java.util.ArrayList;

import com.cognizant.authorizationservice.model.Users;
import com.cognizant.authorizationservice.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;



/**
 * Service implementation class for UserDetailsService
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	/**
	 * Overriding method to load the user details from database with user name
	 * 
	 * @param userName
	 * @return This returns the user name and password
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		log.info("Username={}",username);
		log.info("All={}",userRepo.findAll());
		Users user = userRepo.findByUserName(username);
		return new User(user.getUserName(), user.getPassword(), new ArrayList<>());
	}

}
