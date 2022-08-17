package com.tfg.tms.rest;

import java.util.List;

import com.tfg.tms.entity.Customer;

public interface CustomerRestService {

	public List<Customer> getAllCustomers();

	public Customer getCustomer(int id);

	public void save(Customer customer);

	public void deleteById(int id);

	public List<TicketRestDTO> getCustomerTickets(int customer_id);

	public Customer findByEmail(String email);

}
