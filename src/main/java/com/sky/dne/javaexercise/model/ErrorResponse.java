package com.sky.dne.javaexercise.model;

public class ErrorResponse {

	private String message;
	private ModelState modelState;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ModelState getModelState() {
		return modelState;
	}

	public void setModelState(ModelState modelState) {
		this.modelState = modelState;
	}

}
