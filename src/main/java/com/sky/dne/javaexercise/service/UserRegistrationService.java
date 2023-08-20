package com.sky.dne.javaexercise.service;

import com.sky.dne.javaexercise.dto.UserDto;
import com.sky.dne.javaexercise.model.UserDetails;
import com.sky.dne.javaexercise.model.UsersListResponse;

public interface UserRegistrationService {

	UsersListResponse getUsers();

	UserDetails findById(long id);

	void updateUser(UserDetails user);

	void createUser(UserDto user);

	void delete(Integer id);

}
