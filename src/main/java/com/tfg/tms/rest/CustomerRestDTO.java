package com.tfg.tms.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tfg.tms.entity.Customer;
import com.tfg.tms.entity.Ticket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerRestDTO {

	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<TicketRestDTO> tickets;

	public CustomerRestDTO(Customer customer) {
		super();
		this.id = customer.getId();
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
		this.email = customer.getEmail();
		this.createdAt = customer.getCreatedAt();
		this.updatedAt = customer.getUpdatedAt();
		this.tickets = new ArrayList<TicketRestDTO>();

		for (Ticket ticket : customer.getTickets()) {
			this.tickets.add(new TicketRestDTO(ticket));
		}
	}

}
