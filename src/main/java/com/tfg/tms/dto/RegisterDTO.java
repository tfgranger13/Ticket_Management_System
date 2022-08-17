package com.tfg.tms.dto;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

/*
 * This class is a data transfer object used when visitors are registering to the site
 */

@Data
public class RegisterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "First name can not be empty")
	private String firstName;

	@NotEmpty(message = "Last name can not be empty")
	private String lastName;

	@NotEmpty(message = "Email can not be empty")
	@Email(message = "Please provide a valid email")
	private String email;

	@Size(min = 8, max = 60, message = "Password should be between 8 and 60 characters")
	private String password;

	private String confirmPassword;

	@AssertTrue(message = "Password and Confirm Password must match")
	public Boolean passMatch() {
		return getPassword().equals(getConfirmPassword());
	}
}
