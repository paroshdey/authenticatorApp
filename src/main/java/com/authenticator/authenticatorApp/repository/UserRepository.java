package com.authenticator.authenticatorApp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.authenticator.authenticatorApp.entity.UserInfo;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {

	
	@Query("SELECT u FROM UserInfo u WHERE u.username = :username")
	public UserInfo getUserByUsername(@Param("username") String username);
 
}
