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

import com.tfg.tms.dao.MessageDAO;
import com.tfg.tms.entity.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Rollback
@SpringBootTest(properties = { "logging.file=logs/TMStesting.log" })
public class MessageDAOTest {

	@Autowired
	private MessageDAO messageDAO;

	// before each test, log the name of the test being run
	@BeforeEach
	public void logTest(TestInfo testInfo) {
		log.warn("Running test " + testInfo.getDisplayName());
	}

	@Test
	public void testRepositoryIsNotNull() {
		assertNotNull(messageDAO);
	}

	@Test
	public void testGetMessages() {
		Message newMessage = new Message();
		newMessage.setContent("TesterContent");
		messageDAO.saveMessage(newMessage);
		List<Message> allMessages = messageDAO.getMessages();
		assertEquals(allMessages.contains(newMessage), true);
	}

	@ParameterizedTest(name = "{index}-Run Test with args={0}")
	@ValueSource(ints = { 3, 4, 5 })
	public void testGetMessages(int id) {
		List<Message> allMessages = messageDAO.getMessages();
		Message testMessage = messageDAO.getMessage(id);
		assertTrue(allMessages.contains(testMessage));
	}

	@ParameterizedTest(name = "{index}-Run Test with args={0}")
	@ValueSource(ints = { 13, 14, 87, 193 })
	public void testGetMessagesNotInDb(int id) {
		List<Message> allMessages = messageDAO.getMessages();
		Message testMessage = messageDAO.getMessage(id);
		assertFalse(allMessages.contains(testMessage));
	}

	@Test
	public void testGetMessage() {
		Message testMessage = messageDAO.getMessage(3);
		assertEquals("will this one work too?", testMessage.getContent());
	}

	@Test
	public void testSaveMessage() {
		Message newMessage = new Message();
		newMessage.setContent("TesterContent");
		messageDAO.saveMessage(newMessage);
		List<Message> allMessages = messageDAO.getMessages();
		assertTrue(allMessages.contains(newMessage));
	}

	@Test
	public void testUpdateMessage() {

		Message originalMessage = messageDAO.getMessage(3);
		originalMessage.setContent("Updated");
		messageDAO.saveMessage(originalMessage);
		Message testMessage = messageDAO.getMessage(3);
		assertEquals("Updated", testMessage.getContent());
	}

	@Test
	public void testDeleteMessage() {
		Message testMessage = messageDAO.getMessage(3);
		messageDAO.deleteMessage(3);
		List<Message> allMessages = messageDAO.getMessages();
		assertFalse(allMessages.contains(testMessage));
	}

}
