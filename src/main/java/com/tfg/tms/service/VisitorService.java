package com.tfg.tms.service;

import com.tfg.tms.configuration.CustomerAlreadyExistException;
import com.tfg.tms.dto.LoginDTO;
import com.tfg.tms.dto.RegisterDTO;
import com.tfg.tms.entity.Customer;
import com.tfg.tms.entity.Employee;

/*
 * This class is the interface for the visitor service
 */

public interface VisitorService {

	// services for visitors

	public Customer getCustomerByEmail(String email);

	public void register(RegisterDTO registerDTO) throws CustomerAlreadyExistException;

	public Customer loginCustomer(LoginDTO loginDTO);

	public Employee loginEmployee(LoginDTO loginDTO);

	boolean checkIfUserExist(String email);

	public Customer findByEmail(String email);

}
