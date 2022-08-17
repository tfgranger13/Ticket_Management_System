package com.tfg.tms.dto;

import java.time.LocalDateTime;

import com.tfg.tms.entity.Message;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * This class is a data transfer object used by the admin when creating/updating a message
 */

@Data
@NoArgsConstructor
public class AdminMessageDTO {

	private Integer id;
	private Integer ticket_id;
	private Integer customer_id;
	private Integer employee_id;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public AdminMessageDTO(Message message) {
		this.id = message.getId();
		if (message.getEmployee() == null) {
			this.employee_id = null;
		} else {
			this.employee_id = message.getEmployee().getId();
		}
		if (message.getCustomer() == null) {
			this.customer_id = null;
		} else {
			this.customer_id = message.getCustomer().getId();
		}
		this.content = message.getContent();
		this.createdAt = message.getCreatedAt();
		this.updatedAt = message.getUpdatedAt();
	}
}
