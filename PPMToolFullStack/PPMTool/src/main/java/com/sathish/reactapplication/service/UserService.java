package com.sathish.reactapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sathish.reactapplication.domain.User;
import com.sathish.reactapplication.exception.UsernameAlreadyExistsException;
import com.sathish.reactapplication.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User user) {
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setConfirmPassword(bCryptPasswordEncoder.encode(user.getConfirmPassword()));
		 return userRepository.save(user);
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username "+user.getUsername()+" already exists");
		}
	}

}
