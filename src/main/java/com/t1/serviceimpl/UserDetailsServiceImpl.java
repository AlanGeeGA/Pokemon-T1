package com.t1.serviceimpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.t1.entity.UserEntity;
import com.t1.exception.NotFoundExeception;
import com.t1.repository.UserDetailsRepository;

import io.jsonwebtoken.lang.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserDetailsRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user = userRepository.findByUsername(username);
		
		if(user == null) {
			throw new NotFoundExeception("the user doesnt exists");
		}
		
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
		//return new User("miki@gmail.com","123", new ArrayList<>());
	}

}
