package com.oauth2.google.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oauth2.google.model.user.User;

/**
 * Interface of user Repository
 * 
 * @author Ines Heni
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Find user by email
	 * 
	 * @param email
	 * @return
	 */
	User findByEmail(String email);

	/**
	 * Check if user exist by email
	 * 
	 * @param email
	 * @return
	 */
	boolean existsByEmail(String email);

}
