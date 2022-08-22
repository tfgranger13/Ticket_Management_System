package com.tfg.tms.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tfg.tms.dto.RestTicketDTO;
import com.tfg.tms.entity.Customer;
import com.tfg.tms.entity.Ticket;

/*
 * This class is the interface for the customer dao and declares which methods must be included in its implementation
 */

public interface CustomerDAO {

	public List<Customer> getCustomers();

	public Customer getCustomer(Integer id);

	public Customer getCustomerByEmail(String email);

	public void saveCustomer(Customer customer);

	public void deleteCustomer(Integer id);

	public void removeCustomerTickets(Integer id);

	public Set<Ticket> getCustomerTickets(Integer id);

	public List<RestTicketDTO> getCustomerTicketDTOs(Integer id);

	public Map<Integer, Set<Ticket>> getAllCustomerTickets();

}
