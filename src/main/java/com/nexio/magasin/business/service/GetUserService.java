package com.nexio.magasin.business.service;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nexio.magasin.domain.entity.User;
import com.nexio.magasin.domain.repository.UserRepository;

@Service
public class GetUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Optional<User> execute(String identity, String password){
		
		Optional<User> optUser = userRepository.findByEmail(identity);
		
		if(!optUser.isPresent()) return Optional.empty();
		
		User user = optUser.get();
		
		String sha256hex = DigestUtils.sha256Hex(password);
		
		return sha256hex.equals(user.getHashedPassword()) ? optUser : Optional.empty();
	}
}
