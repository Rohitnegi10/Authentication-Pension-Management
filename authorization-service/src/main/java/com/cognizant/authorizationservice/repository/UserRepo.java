package com.cognizant.authorizationservice.repository;

import com.cognizant.authorizationservice.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for storing, fetching and manipulating user data
 */
@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

	// Method to find a user details with user name
	public Users findByUserName(String name);
}
