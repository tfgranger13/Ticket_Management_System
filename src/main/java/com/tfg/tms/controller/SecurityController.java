package com.tfg.tms.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * This class is a controller that contains an endpoint that will show the email address of the currently logged in user
 */

@Controller
public class SecurityController {

	// this endpoint will show the username(email) for the currently logged in user
	@RequestMapping(value = "/username", method = RequestMethod.GET)
	@ResponseBody
	public String currentUserName(Principal principal) {
		try {
			// return the principal's username(email)
			return principal.getName();
		} catch (Exception e) {
			// if no one's logged in, return a message
			return "No user is currently logged in";
		}
	}
}
