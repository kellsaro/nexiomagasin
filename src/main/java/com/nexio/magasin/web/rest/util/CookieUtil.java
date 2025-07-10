package com.nexio.magasin.web.rest.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
	
	public void setCookies(HttpServletResponse response, Collection<Entry<String, Object>> entries) {
		
		entries.stream()
			   .forEach(e -> {
				   
				   Cookie cookie = new Cookie(e.getKey(), e.getValue().toString());
				   
				   cookie.setMaxAge(7 * 24 * 60 * 60);
				   cookie.setHttpOnly(true);
				   cookie.setPath("/");
				   
				   response.addCookie(cookie); });
	} 
	
	public Map<String, Object> buildCookiesMap(){
		return new HashMap<>();
	}
}
