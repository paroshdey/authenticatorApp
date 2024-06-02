package com.authenticator.authenticatorApp.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.authenticator.authenticatorApp.MyUserDetails;
import com.authenticator.authenticatorApp.entity.UserInfo;
import com.authenticator.authenticatorApp.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("fetching user info username"+username);
		
		UserInfo user = userRepo.getUserByUsername(username);
		
		System.out.println("fetched user ::"+user.getUsername());
		
		if(user == null)
		{
			throw new UsernameNotFoundException(username);
		}
		return new MyUserDetails(user);
	}

}
