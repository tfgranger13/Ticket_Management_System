package com.tfg.tms.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tfg.tms.entity.Customer;
import com.tfg.tms.entity.Ticket;
import com.tfg.tms.rest.TicketRestDTO;

/*
 * This class connects to the database to perform CRUD operations on Customers
 */

// links with Spring
@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// define field for entitymanager
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public CustomerDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	// transactions handled in service
	public List<Customer> getCustomers() {

		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);

		// create a query
		Query<Customer> query = currentSession.createQuery("from Customer", Customer.class);

		// execute the query and get results
		List<Customer> customers = query.getResultList();

		// return the results
		return customers;
	}

	@Override
	public Customer getCustomer(Integer id) {
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);

		// get the employee
		Customer customer = currentSession.get(Customer.class, id);

		return customer;
	}

	@Override
	public Customer getCustomerByEmail(String email) throws NoResultException {

		try {

			// get the current session from hibernate
			Session currentSession = entityManager.unwrap(Session.class);

			// create a query
			Query<Customer> query = currentSession.createQuery("from Customer c where c.email=:email", Customer.class);

			// set the parameter
			query.setParameter("email", email);

			// return the customer
			return query.getSingleResult();
		}

		// if the user doesn't exist, it throws an error
		catch (NoResultException e) {
			// so return null instead
			return null;
		}
	}

	@Override
	public void saveCustomer(Customer customer) {
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		// save the user in the session
		currentSession.saveOrUpdate(customer);
	}

	@Override
	public void deleteCustomer(Integer id) {
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		// get the employee
		Customer customer = currentSession.get(Customer.class, id);
		// save the user in the session
		currentSession.delete(customer);
	}

	@Override
	public void removeCustomerTickets(Integer id) {
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		// get the employee
		Customer customer = currentSession.get(Customer.class, id);
		// get the employee's tickets
		Set<Ticket> custTickets = customer.getTickets();
		// loop thru the tickets and set the employee to null
		// this saves the ticket when the employee is deleted
		for (Ticket ticket : custTickets) {
			ticket.setCustomer(null);
			currentSession.update(ticket);
		}
	}

	@Override
	public Set<Ticket> getCustomerTickets(Integer id) {
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		// get the employee
		Customer customer = currentSession.get(Customer.class, id);
		Set<Ticket> tickets = customer.getTickets();
		return tickets;
	}

	@Override
	public List<TicketRestDTO> getCustomerTicketDTOs(Integer id) {

		// get the session
		Session session = entityManager.unwrap(Session.class);
		// get the customer
		Customer customer = session.get(Customer.class, id);
		// get their set of tickets
		Set<Ticket> tickets = customer.getTickets();
		// make a list to return the ticket DTOs in
		List<TicketRestDTO> ticketDTOs = new ArrayList<TicketRestDTO>();
		for (Ticket ticket : tickets) {
			TicketRestDTO ticketDTO = new TicketRestDTO(ticket);
			ticketDTOs.add(ticketDTO);
		}
		// return the results
		return ticketDTOs;
	}

	/*
	 * this method was used to present all tickets for each customer on the
	 * admin/list page but it is no longer being used. It was cool and took a lot of
	 * work so I'm keeping it in.
	 */
	@Override
	public Map<Integer, Set<Ticket>> getAllCustomerTickets() {
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		// get all the customers
		Query<Customer> query = currentSession.createQuery("from Customer", Customer.class);
		List<Customer> customers = query.getResultList();
		// create the map to hold the info
		Map<Integer, Set<Ticket>> customerTicketsMap = new HashMap<Integer, Set<Ticket>>();
		Integer id = null;
		Set<Ticket> ticketSet = new HashSet<Ticket>();
		// for each customer, put their id and set of tickets in a map
		for (Customer customer : customers) {
			id = customer.getId();
			for (Ticket ticket : customer.getTickets()) {
				ticketSet.add(ticket);
			}
			customerTicketsMap.put(id, ticketSet);
			// reset the set for the next customer
			ticketSet = new HashSet<Ticket>();
		}

		return customerTicketsMap;
	}

}
