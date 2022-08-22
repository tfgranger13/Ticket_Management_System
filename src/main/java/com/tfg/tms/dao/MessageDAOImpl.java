package com.tfg.tms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tfg.tms.entity.Message;
import com.tfg.tms.entity.Ticket;

/*
 * This class connects to the database to perform CRUD operations on messages
 */

@Repository
public class MessageDAOImpl implements MessageDAO {

	// define field for entitymanager
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public MessageDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Message> getMessages() {
		Session session = entityManager.unwrap(Session.class);
		Query<Message> query = session.createQuery("from Message", Message.class);
		List<Message> messages = query.getResultList();
		return messages;
	}

	@Override
	public Message getMessage(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		Message message = session.get(Message.class, id);
		return message;
	}

	@Override
	public void saveMessage(Message message) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(message);
	}

	@Override
	public void deleteMessage(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		Message message = session.get(Message.class, id);
		session.delete(message);
	}

	@Override
	public List<Message> getMessagesFromTicket(Integer id) {
		Session session = entityManager.unwrap(Session.class);

		Ticket ticket = session.get(Ticket.class, id);

		Set<Message> messages = ticket.getMessages();

		List<Message> messagesList = new ArrayList<Message>(messages);

		return messagesList;
	}

}
