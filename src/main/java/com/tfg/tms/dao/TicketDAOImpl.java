package com.tfg.tms.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tfg.tms.entity.Ticket;

/*
 * This class connects to the database to perform CRUD operations on tickets
 */

@Repository
public class TicketDAOImpl implements TicketDAO {

	// define field for entitymanager
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public TicketDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Ticket> getTickets() {
		Session session = entityManager.unwrap(Session.class);
		Query<Ticket> query = session.createQuery("from Ticket", Ticket.class);
		List<Ticket> tickets = query.getResultList();
		return tickets;
	}

	@Override
	public Ticket getTicket(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		Ticket ticket = session.get(Ticket.class, id);
		return ticket;
	}

	@Override
	public void saveTicket(Ticket ticket) {
		Session session = entityManager.unwrap(Session.class);
		// was getting an error when trying to update a ticket
		// if it's an update, the id is not null
		if (ticket.getId() != null) {
			// merge the new object with the one in memory
			session.merge(ticket);
		} else {
			// else it's new, save it
			session.save(ticket);
		}
	}

	@Override
	public void deleteTicket(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		Ticket ticket = session.get(Ticket.class, id);
		session.delete(ticket);
	}

}
