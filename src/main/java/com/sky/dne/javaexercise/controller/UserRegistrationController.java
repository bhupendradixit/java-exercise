package com.sky.dne.javaexercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sky.dne.javaexercise.dto.UserDto;
import com.sky.dne.javaexercise.model.UserDetails;
import com.sky.dne.javaexercise.model.UsersListResponse;
import com.sky.dne.javaexercise.service.UserRegistrationService;

@Controller
public class UserRegistrationController {

	@Autowired
	private UserRegistrationService userRegistrationService;

	//method to open registration form
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return "register";
	}

	//method to handle register user form submit request
	@PostMapping("/save-registration")
	public String registration(@ModelAttribute("user") UserDto user, BindingResult result, Model model) {
//		User existing = userService.findByEmail(user.getEmail());
//		if (existing != null) {
//			result.rejectValue("email", null, "There is already an account registered with that email");
//		}

		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "register";
		}
		userRegistrationService.createUser(user);
		return "redirect:list";
	}

	//method to fetch/display the list of users
	@GetMapping("list")
	public String showUpdateForm(Model model) {
		UsersListResponse response = userRegistrationService.getUsers();
		model.addAttribute("users", response.getData());
		return "index";
	}

	//method to open edit user form
	@GetMapping("user/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		UserDetails user = userRegistrationService.findById(id);
//				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("user", user);
		return "update-user";
	}

	//method to handle edit user form submit request - update user 
	@PostMapping("user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, /* @Validated */ /* UserDetails */ UserDetails user,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			user.setId(id);
			return "update-user";
		}
//		try {
		userRegistrationService.updateUser(user);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		model.addAttribute("users", userRegistrationService.getUsers().getData());
		return "index";
	}

	//method to handle delete user request by id
	@GetMapping("user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		try {
			userRegistrationService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("users", userRegistrationService.getUsers().getData());
		return "index";
	}

}
