package com.tfg.tms.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tfg.tms.dto.AdminTicketDTO;
import com.tfg.tms.dto.CustomerTicketDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * This class represents the tickets on the site. They are persisted in the database
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String detail;

	private String status;

	private String priority;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", insertable = false)
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Message> messages = new HashSet<Message>();

	public Ticket(AdminTicketDTO binder) {
		this.id = binder.getTicket_id();
		this.detail = binder.getDetail();
		this.status = binder.getStatus();
		this.priority = binder.getPriority();
		this.createdAt = binder.getCreatedAt();
		this.updatedAt = binder.getUpdatedAt();
	}

	public Ticket(CustomerTicketDTO binder) {
		this.detail = binder.getDetail();
		this.priority = binder.getPriority();
	}
}
