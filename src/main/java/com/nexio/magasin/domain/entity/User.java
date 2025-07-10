package com.nexio.magasin.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Email
	@NotNull
	@NotBlank
	private String email;
	
	/**
	 * Password hashed with SHA-256 algorithm
	 */
	@Column(name = "hashed_password")
	@NotNull
	@NotBlank
	private String hashedPassword;
	
	@Column(name = "session_token")
	private String sessionToken;
	
	public void setPassword(String password) {
		this.hashedPassword = DigestUtils.sha256Hex(password);
	}
}
