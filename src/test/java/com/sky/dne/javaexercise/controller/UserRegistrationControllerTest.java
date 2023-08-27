package com.sky.dne.javaexercise.controller;

import com.sky.dne.javaexercise.dto.UserDto;
import com.sky.dne.javaexercise.model.UserDetails;
import com.sky.dne.javaexercise.model.UsersListResponse;
import com.sky.dne.javaexercise.service.UserRegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(value = UserRegistrationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@WebAppConfiguration
public class UserRegistrationControllerTest {

	@MockBean
	private UserRegistrationService userRegistrationService;

//	@InjectMocks
//	private UserRegistrationController userRegistrationController;

	@Mock
	private UserDetails userDetails;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testShowRegistrationForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/register")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("register"));
	}

	@Test
	void testRegistration_WithValidUser() throws Exception {
		UserDto userDto = new UserDto();
		mockMvc.perform(MockMvcRequestBuilders.post("/save-registration").flashAttr("user", userDto))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("list"));

		verify(userRegistrationService).createUser(userDto);
	}

	@Test
	void testShowUpdateForm() throws Exception {
		when(userRegistrationService.getUsers()).thenReturn(new UsersListResponse());
		mockMvc.perform(MockMvcRequestBuilders.get("/list")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("index"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("users"));
	}

	@Test
	void testShowUpdateFormById() throws Exception {
		when(userRegistrationService.findById(1L)).thenReturn(userDetails);
		mockMvc.perform(MockMvcRequestBuilders.get("/user/edit/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("update-user"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("user"));
	}

	@Test
	void testUpdateUser_WithValidUser() throws Exception {
		UsersListResponse userList = new UsersListResponse();
		List<UserDetails> data = new ArrayList<>();
		UserDetails userDetails = new UserDetails();
		userDetails.setId(1);
		userDetails.setName("Bhupendra");
		userDetails.setEmail("abc@gmail.com");
		userDetails.setLocation("India");
		data.add(userDetails);
		userList.setData(data);

		when(userRegistrationService.getUsers()).thenReturn(userList);
		mockMvc.perform(MockMvcRequestBuilders.post("/user/update/1").flashAttr("user", userDetails))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("index"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("users"));

		verify(userRegistrationService).updateUser(userDetails);
	}

	@Test
	void testDeleteUser() throws Exception {
		UsersListResponse userList = new UsersListResponse();
		List<UserDetails> data = new ArrayList<>();
		UserDetails userDetails = new UserDetails();
		userDetails.setId(1);
		userDetails.setName("Bhupendra");
		userDetails.setEmail("abc@gmail.com");
		userDetails.setLocation("India");
		data.add(userDetails);
		userList.setData(data);

		when(userRegistrationService.getUsers()).thenReturn(userList);
		mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("index"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("users"));

		verify(userRegistrationService).delete(1);
	}

}
