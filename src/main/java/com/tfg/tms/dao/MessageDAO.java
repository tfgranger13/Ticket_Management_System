package com.tfg.tms.dao;

import java.util.List;

import com.tfg.tms.entity.Message;

/*
 * This class is the interface for the message dao and declares which methods must be included in its implementation
 */

public interface MessageDAO {

	public List<Message> getMessages();

	public Message getMessage(Integer id);

	public void saveMessage(Message message);

	public void deleteMessage(Integer id);

	public List<Message> getMessagesFromTicket(Integer id);

}
