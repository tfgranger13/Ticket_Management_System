package com.tfg.tms.dto;

import lombok.Data;

/*
 * This class is a data transfer object used when visitors are attempting to log in
 */

@Data
public class LoginDTO {
	private String email;
	private String password;
}
