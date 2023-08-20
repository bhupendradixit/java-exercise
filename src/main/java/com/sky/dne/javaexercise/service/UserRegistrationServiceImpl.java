package com.sky.dne.javaexercise.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.dne.javaexercise.dto.UserDto;
import com.sky.dne.javaexercise.model.RegistrationResponse;
import com.sky.dne.javaexercise.model.UserDetails;
import com.sky.dne.javaexercise.model.UsersListResponse;
import com.sky.dne.javaexercise.util.Constants;
import com.sky.dne.javaexercise.util.RestTemplateHelper;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	@Autowired
	private RestTemplateHelper restTemplateHelper;

	@Override
	public UsersListResponse getUsers() {
		return restTemplateHelper.getForEntity(UsersListResponse.class, Constants.FETCH_USERS_URL, null);
	}

	@Override
	public UserDetails findById(long id) {
		return restTemplateHelper.getForEntity(UserDetails.class, Constants.FIND_USER_BY_ID_URL + id, null);
	}

	@Override
	public void updateUser(UserDetails user) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", user.getId().toString());
		restTemplateHelper.putForEntity(Constants.UPDATE_USER_URL, user, params);

	}

	@Override
	public void createUser(UserDto user) {
		restTemplateHelper.postForEntity(RegistrationResponse.class, Constants.REGISTER_USER, user, null);

	}

	@Override
	public void delete(Integer id) {
		restTemplateHelper.delete(Constants.DELETE_USER + id);

	}

}
