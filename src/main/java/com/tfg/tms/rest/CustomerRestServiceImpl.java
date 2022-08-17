package com.tfg.tms.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.tms.dao.CustomerDAO;
import com.tfg.tms.dao.CustomerRepository;
import com.tfg.tms.entity.Customer;

@Service
public class CustomerRestServiceImpl implements CustomerRestService {

	private CustomerDAO customerDAO;
	private CustomerRepository customerRepository;

	@Autowired
	public CustomerRestServiceImpl(CustomerDAO customerDAO, CustomerRepository customerRepository) {
		this.customerDAO = customerDAO;
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional
	public List<Customer> getAllCustomers() {
		return customerDAO.getCustomers();
	}

	@Override
	@Transactional
	public Customer getCustomer(int id) {
		return customerDAO.getCustomer(id);
	}

	@Override
	@Transactional
	public void save(Customer customer) {
		customerDAO.saveCustomer(customer);

	}

	@Override
	@Transactional
	public void deleteById(int id) {
		customerDAO.removeCustomerTickets(id);
		customerDAO.deleteCustomer(id);

	}

	@Override
	@Transactional
	public List<TicketRestDTO> getCustomerTickets(int customer_id) {
		return customerDAO.getCustomerTicketDTOs(customer_id);
	}

	@Override
	@Transactional
	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

}
