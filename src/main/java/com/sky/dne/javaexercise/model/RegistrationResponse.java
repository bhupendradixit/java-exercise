package com.sky.dne.javaexercise.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationResponse {

	private Integer code;
	@JsonProperty("message")
	@JsonAlias("Message")
	private String message;
	private User data;
	private ModelState modelState;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getData() {
		return data;
	}

	public void setData(User data) {
		this.data = data;
	}

	public ModelState getModelState() {
		return modelState;
	}

	public void setModelState(ModelState modelState) {
		this.modelState = modelState;
	}

}
