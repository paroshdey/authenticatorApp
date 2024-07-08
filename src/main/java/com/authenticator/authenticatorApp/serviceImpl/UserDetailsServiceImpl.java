package com.authenticator.authenticatorApp.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.authenticator.authenticatorApp.MyUserDetails;
import com.authenticator.authenticatorApp.entity.UserInfo;
import com.authenticator.authenticatorApp.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		logger.info("user found ");
		
		UserInfo user = userRepo.getUserByUsername(username);
		
		if(user != null ) {
			return new MyUserDetails(user);	
		}
		else {
			throw new UsernameNotFoundException("user not found");
		}
		
	}

}
