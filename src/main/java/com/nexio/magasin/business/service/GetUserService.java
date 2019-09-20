package com.nexio.magasin.business.service;

import java.util.Optional;

import com.nexio.magasin.domain.entity.User;

public interface GetUserService {
	
	Optional<User> execute(String identity, String password);
}
