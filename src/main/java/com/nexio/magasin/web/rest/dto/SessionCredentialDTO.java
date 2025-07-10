package com.nexio.magasin.web.rest.dto;

import lombok.Data;

@Data
public class SessionCredentialDTO {
	private String identity;
	private String token;
}
