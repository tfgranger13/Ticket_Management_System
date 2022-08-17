package com.tfg.tms.dto;

import java.time.LocalDateTime;

import com.tfg.tms.entity.Ticket;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * This is a data transfer object used when an admin is creating/updating a ticket
 */

@Data
@NoArgsConstructor
public class AdminTicketDTO {
	private Integer ticket_id;
	private Integer employee_id;
	private Integer customer_id;
	private Integer department_id;
	private String detail;
	private String status;
	private String priority;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public AdminTicketDTO(Ticket ticket) {
		this.ticket_id = ticket.getId();
		if (ticket.getEmployee() == null) {
			this.employee_id = null;
		} else {
			this.employee_id = ticket.getEmployee().getId();
		}
		if (ticket.getCustomer() == null) {
			this.customer_id = null;
		} else {
			this.customer_id = ticket.getCustomer().getId();
		}
		if (ticket.getDepartment() == null) {
			this.department_id = null;
		} else {
			this.department_id = ticket.getDepartment().getId();
		}
		this.detail = ticket.getDetail();
		this.status = ticket.getStatus();
		this.priority = ticket.getPriority();
		this.createdAt = ticket.getCreatedAt();
		this.updatedAt = ticket.getUpdatedAt();
	}

}
