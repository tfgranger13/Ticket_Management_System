package com.tfg.tms.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tfg.tms.dto.RegisterDTO;
import com.tfg.tms.entity.Customer;
import com.tfg.tms.exceptions.CustomerAlreadyExistsException;
import com.tfg.tms.service.VisitorService;

import lombok.extern.slf4j.Slf4j;

/*
 * This class is the controller for visitors to the website.
 * It will direct them to the welcome page where they can register or login.
 */

@Controller
@Slf4j
//declare the customer as a session attribute to be used throughout the controller
@SessionAttributes("customer")
public class VisitorController {

	// by making the customer a model attribute, we can easily reference it in the
	// methods we write for the controller
	@ModelAttribute("customer")
	public Customer getCustomerFromLogin(HttpServletRequest request) {
		try {
			// get the principal for the currently logged in user from the request
			Principal principal = request.getUserPrincipal();
			// get the customer by the email used to log in
			Customer customer = visitorService.findByEmail(principal.getName());
			// return the customer
			return customer;
		} catch (Exception e) {
			return null;
		}
	}

	@Autowired
	private VisitorService visitorService;

	// route to welcome a visitor when they first visit the site
	@GetMapping("/welcome")
	public String listAdmin() {
		return "welcome";
	}

	// endpoint to send visitors to the form to register
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("registerDTO", new RegisterDTO());
		return "register";
	}

	// endpoint for registering the customer from the information in the form
	@PostMapping("/register")
	public String customerRegistration(@Valid RegisterDTO registerDTO, BindingResult bindingResult, Model model) {

		// run the method to see if the password and confirm password do NOT match
		if (!registerDTO.passMatch()) {
			// if not, add a new error to the binding result so it gets shown to the user
			bindingResult.addError(
					new FieldError("bindingResult", "confirmPassword", "Password and Confirm password must match"));
		}
		// if there are any other errors in the form, send them back with the info they
		// entered to fix
		if (bindingResult.hasErrors()) {
			model.addAttribute("registerDTO", registerDTO);
			return "register";
		}
		// if there are no errors, try to register the customer with the service
		try {
			visitorService.register(registerDTO);
		}
		// catch the exception if the email is in use already
		catch (CustomerAlreadyExistsException e) {
			bindingResult.rejectValue("email", "userData.email", "An account already exists for this email.");
			model.addAttribute("registerDTO", registerDTO);
			return "register";
		}
		// log the transaction
		log.info("registered " + registerDTO.getEmail());
		return "redirect:/login";
	}

	// endpoint to send visitors to the customer login form
	@GetMapping("/login")
	String login() {
		return "login";
	}

//	// this endpoint will log the following messages to test and make sure the
//	// logging is set up correctly
//	@RequestMapping("/checklogs")
//	public String demoLogs() {
//		log.trace("This is a trace log example");
//		log.info("This is an info log example");
//		log.debug("This is a debug log example");
//		log.error("This is an error log example");
//		log.warn("This is a warn log example");
//		return "redirect:/welcome";
//	}

//	// use HttpServletRequest request to get access to the currently logged in user
//	@GetMapping("/welcome")
//	public String successCustomers(HttpServletRequest request, Model model) {
//
//		// get the principal for the currently logged in user from the request
//		Principal principal = request.getUserPrincipal();
//
//		// get the customer by the email used to log in
//		Customer customer = customerRepo.findByEmail(principal.getName());
//
//		// add the customer to the model as the user
//		model.addAttribute("user", customer);
//
//		// you can also directly reference the email for the currently logged in user in
//		// thymeleaf using <span sec:authentication="name"></span>
//
//		return "/customer/success.html";
//	}

}
