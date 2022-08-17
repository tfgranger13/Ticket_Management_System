package com.tfg.tms.rest;

import java.time.LocalDateTime;

import com.tfg.tms.entity.Message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * This is a data transfer object used to send a message .json file through the rest controller
 */

@Setter
@Getter
@NoArgsConstructor
public class MessageRestDTO {

	private Integer id;
	private String from;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public MessageRestDTO(Message message) {
		this.id = message.getId();
		this.content = message.getContent();
		this.createdAt = message.getCreatedAt();
		this.updatedAt = message.getUpdatedAt();

		if (message.getEmployee() != null) {
			this.from = message.getEmployee().getFirstName() + " " + message.getEmployee().getLastName();
		} else {
			this.from = message.getCustomer().getFirstName() + " " + message.getCustomer().getLastName();
		}
	}
}
