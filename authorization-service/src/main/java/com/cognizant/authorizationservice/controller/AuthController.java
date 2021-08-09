package com.cognizant.authorizationservice.controller;

import java.util.HashMap;
import java.util.Map;

import com.cognizant.authorizationservice.exception.UserNameNumericException;
import com.cognizant.authorizationservice.exception.UserNotFoundException;
import com.cognizant.authorizationservice.model.UserCredentials;
import com.cognizant.authorizationservice.service.UserDetailsServiceImpl;
import com.cognizant.authorizationservice.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;

/**
 * Class for Authorization Controller
 */
@RestController
@Slf4j
@CrossOrigin
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;


	@GetMapping("/validate")
	public ResponseEntity<Boolean> validate(@RequestHeader(name = "Authorization") String token1) {
		String token = token1.substring(7);
		try {
			UserDetails user = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
			
			if (jwtUtil.validateToken(token, user)) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}
	}


	/**
	 * Method to check whether login credentials are correct or not
	 * 
	 * @param userCredentials user credentials contain user name and password
	 * @return This returns token on successful login else throws exception
	 */
	@PostMapping("/getToken")
	public Map<String,String> login(@RequestBody UserCredentials userCredentials) {
		Map<String, String> token = new HashMap<>();

		if (userCredentials.getUserName() == null || userCredentials.getPassword() == null
				|| userCredentials.getUserName().trim().isEmpty() || userCredentials.getPassword().trim().isEmpty()) {
			throw new UserNotFoundException("User name or password cannot be Null or Empty");
		}

		else if (jwtUtil.isNumeric(userCredentials.getUserName())) {
			throw new UserNameNumericException("User name is numeric");
		}

		else {
			try {
				UserDetails user = userDetailsService.loadUserByUsername(userCredentials.getUserName());
				if (user.getPassword().equals(userCredentials.getPassword())) {
					String t = jwtUtil.generateToken(user.getUsername());
					log.debug("Login successful");
					token.put("token", t);
					return token;
				} else {
					log.debug("Invalid password");
					throw new UserNotFoundException("Password is wrong");
				}
			} catch (Exception e) {
				log.debug("Invalid Credential");
				log.error("ERROR={}",e.getMessage());
				throw new UserNotFoundException("Invalid Credential");
			}
		}
	}
}
