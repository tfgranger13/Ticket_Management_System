package com.tfg.tms.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tfg.tms.entity.Message;
import com.tfg.tms.entity.Ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * This is a data transfer object used to send a ticket .json file through the rest controller
 */

@Setter
@Getter
@NoArgsConstructor
public class TicketRestDTO {

	private Integer id;
	private String departmentName;
	private String employeeName;
	private String detail;
	private String status;
	private String priority;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<MessageRestDTO> messages;

	public TicketRestDTO(Ticket ticket) {
		this.id = ticket.getId();
		this.departmentName = ticket.getDepartment().getName();
		this.employeeName = ticket.getEmployee().getFirstName() + " " + ticket.getEmployee().getLastName();
		this.detail = ticket.getDetail();
		this.status = ticket.getStatus();
		this.priority = ticket.getPriority();
		this.createdAt = ticket.getCreatedAt();
		this.updatedAt = ticket.getUpdatedAt();
		this.messages = new ArrayList<MessageRestDTO>();

		for (Message message : ticket.getMessages()) {
			this.messages.add(new MessageRestDTO(message));
		}
	}

}
