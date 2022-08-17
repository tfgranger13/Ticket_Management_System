package com.tfg.tms.dao;

import java.util.List;

import com.tfg.tms.entity.Ticket;

/*
 * This class is the interface for the message dao and declares which methods must be included in its implementation
 */

public interface TicketDAO {

	public List<Ticket> getTickets();

	public Ticket getTicket(Integer id);

	public void saveTicket(Ticket ticket);

	public void deleteTicket(Integer id);

}
