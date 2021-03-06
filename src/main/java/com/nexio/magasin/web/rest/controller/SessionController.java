package com.nexio.magasin.web.rest.controller;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexio.magasin.business.service.DeleteUserSessionService;
import com.nexio.magasin.business.service.GetUserService;
import com.nexio.magasin.business.service.LinkCartWithUserService;
import com.nexio.magasin.domain.entity.User;
import com.nexio.magasin.domain.repository.UserRepository;
import com.nexio.magasin.web.rest.dto.CredentialDTO;
import com.nexio.magasin.web.rest.util.CookieUtil;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1")
@Api(value="Contrôleur de session", description="Operations de connexion et déconnexion à un compte utilisateur")
public class SessionController {
	
	@Autowired
	private GetUserService getUserService;
	
	@Autowired
	private LinkCartWithUserService linkCartWithUserService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CookieUtil cookieUtil;
	
	@Autowired
	private DeleteUserSessionService deleteUserSessionService;

	@PostMapping("/login")
	public ResponseEntity<?> create(@RequestBody CredentialDTO credentials, @CookieValue(required = false, name = "cart_id") Long cartId, HttpServletResponse response) {
		
		Optional<User> optUser = getUserService.execute(credentials.getEmail(), credentials.getPassword());
		
		if(!optUser.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		User user = optUser.get();
		
		if(user.getSessionToken() != null) return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		
		String token = UUID.randomUUID().toString();
		
		user.setSessionToken(token);
		userRepository.save(user);
		
		if(cartId != null) {
			linkCartWithUserService.execute(cartId, user);
		}
		
		Map<String, Object> cookies = cookieUtil.buildCookiesMap();
		cookies.put("token", token);
		
		cookieUtil.setCookies(response, cookies.entrySet());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/logout")
	public ResponseEntity<?> delete(@CookieValue(required = true, name = "token") String token) {
		
		boolean deleted = deleteUserSessionService.execute(token);
		
		return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
