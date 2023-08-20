package com.sky.dne.javaexercise.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	@JsonProperty("Id")
	private Integer id;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Email")
	private String email;
//	private String password;
	@JsonProperty("Token")
	private String token;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
