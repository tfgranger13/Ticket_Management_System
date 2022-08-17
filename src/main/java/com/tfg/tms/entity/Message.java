package com.tfg.tms.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tfg.tms.dto.AdminMessageDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * This class represents the customers registered on the site. They are persisted in the database
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String content;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", insertable = false)
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public Message(AdminMessageDTO messageDTO) {
		this.id = messageDTO.getId();
		this.content = messageDTO.getContent();
		this.createdAt = messageDTO.getCreatedAt();
		this.updatedAt = messageDTO.getUpdatedAt();
	}
}
