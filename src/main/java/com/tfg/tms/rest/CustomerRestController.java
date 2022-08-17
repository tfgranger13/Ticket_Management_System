package com.tfg.tms.rest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tfg.tms.entity.Customer;

/*
 * This class is a controller for the websit'es Rest services. It will give the logged in user a json file containing their information and tickets.
 */

@RestController
@SessionAttributes("customer")
public class CustomerRestController {

	// by making the customer a model attribute, we can easily reference it in the
	// methods we write for the controller
	@ModelAttribute("customer")
	public Customer getCustomerFromLogin(HttpServletRequest request) {
		try {
			// get the principal for the currently logged in user from the request
			Principal principal = request.getUserPrincipal();
			// get the customer by the email used to log in
			Customer customer = customerRestService.findByEmail(principal.getName());
			// return the customer
			return customer;
		} catch (Exception e) {
			return null;
		}
	}

	private CustomerRestService customerRestService;

	@Autowired
	public CustomerRestController(CustomerRestService customerRestService) {
		this.customerRestService = customerRestService;
	}

	// this is the B2B link, it will grab the currently logged in user from session
	// and return their info. This way you can't brute for other customer's info
	@GetMapping("/api")
	public CustomerRestDTO b2bLink(@ModelAttribute("customer") Customer customer) {
		if (customer == null) {
			throw new RuntimeException("Customer id not found");
		}

		// try updating the user in the session to fix the lazy fetch error?
		// hey it worked. idk why it's necessary tho, strange.
		// It was working before I added any messages
		Customer updatedCustomer = customerRestService.getCustomer(customer.getId());

		CustomerRestDTO customerRestDTO = new CustomerRestDTO(updatedCustomer);
		return customerRestDTO;
	}

//	@GetMapping("/admin/api/customers")
	public List<CustomerRestDTO> getAllCustomers() {
		// get a list of all customers
		List<Customer> customers = customerRestService.getAllCustomers();
		// create a list for the DTOs
		List<CustomerRestDTO> customerDTOs = new ArrayList<CustomerRestDTO>();
		// loop through all the customers in the customer list
		for (Customer customer : customers) {
			// make a new object to send
			// inefficient but it wasn't working by making one new dto and setting the
			// values
			CustomerRestDTO customerDTO = new CustomerRestDTO(customer);
			// add the DTO to the list of DTO's
			customerDTOs.add(customerDTO);
		}
		// return the list of DTOs
		return customerDTOs;
	}

//	@GetMapping("/admin/api/customers/{customerId}")
	public CustomerRestDTO getCustomer(@PathVariable int customerId) {
		Customer customer = customerRestService.getCustomer(customerId);
		if (customer == null) {
			throw new RuntimeException("Customer id not found");
		}
		CustomerRestDTO customerRestDTO = new CustomerRestDTO(customer);
		return customerRestDTO;
	}

}
