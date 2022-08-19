package com.tfg.tms.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.tfg.tms.dao.TicketDAO;
import com.tfg.tms.entity.Ticket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Rollback
@SpringBootTest(properties = { "logging.file=logs/TMStesting.log" })
public class TicketDAOTest {

	@Autowired
	private TicketDAO ticketDAO;

	// before each test, log the name of the test being run
	@BeforeEach
	public void logTest(TestInfo testInfo) {
		log.warn("Running test " + testInfo.getDisplayName());
	}

	@Test
	public void testRepositoryIsNotNull() {
		assertNotNull(ticketDAO);
	}

	@Test
	public void testGetTickets() {
		Ticket newTicket = new Ticket();
		newTicket.setDetail("TesterDetail");
		newTicket.setPriority("TesterPriority");
		newTicket.setStatus("TesterStatus");
		ticketDAO.saveTicket(newTicket);
		List<Ticket> allTickets = ticketDAO.getTickets();
		assertEquals(allTickets.contains(newTicket), true);
	}

	@ParameterizedTest(name = "{index}-Run Test with args={0}")
	@ValueSource(ints = { 1, 3, 4, 5 })
	public void testGetTickets(int id) {
		List<Ticket> allTickets = ticketDAO.getTickets();
		Ticket testTicket = ticketDAO.getTicket(id);
		assertTrue(allTickets.contains(testTicket));
	}

	@ParameterizedTest(name = "{index}-Run Test with args={0}")
	@ValueSource(ints = { 13, 14, 87, 193 })
	public void testGetTicketsNotInDb(int id) {
		List<Ticket> allTickets = ticketDAO.getTickets();
		Ticket testTicket = ticketDAO.getTicket(id);
		assertFalse(allTickets.contains(testTicket));
	}

	@Test
	public void testGetTicket() {
		Ticket testTicket = ticketDAO.getTicket(1);
		assertEquals("Will this one work?", testTicket.getDetail());
	}

	@Test
	public void testSaveTicket() {
		Ticket newTicket = new Ticket();
		newTicket.setDetail("TesterDetail");
		newTicket.setPriority("TesterPriority");
		newTicket.setStatus("TesterStatus");
		ticketDAO.saveTicket(newTicket);
		List<Ticket> allTickets = ticketDAO.getTickets();
		assertTrue(allTickets.contains(newTicket));
	}

	@Test
	public void testUpdateTicket() {

		Ticket originalTicket = ticketDAO.getTicket(1);
		originalTicket.setDetail("Updated");
		ticketDAO.saveTicket(originalTicket);
		Ticket testTicket = ticketDAO.getTicket(1);
		assertEquals("Updated", testTicket.getDetail());
	}

	@Test
	public void testDeleteTicket() {
		Ticket testTicket = ticketDAO.getTicket(1);
		ticketDAO.deleteTicket(1);
		List<Ticket> allTickets = ticketDAO.getTickets();
		assertFalse(allTickets.contains(testTicket));
	}

}
