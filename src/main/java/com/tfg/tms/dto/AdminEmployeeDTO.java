package com.tfg.tms.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.tfg.tms.entity.Employee;
import com.tfg.tms.entity.Ticket;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * This class is a data transfer object used when an admin creates or updates and employee
 */

@Data
@NoArgsConstructor
public class AdminEmployeeDTO {

	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Set<Ticket> tickets;
	private Integer department_id;

	public AdminEmployeeDTO(Employee employee) {
		super();
		this.id = employee.getId();
		this.firstName = employee.getFirstName();
		this.lastName = employee.getLastName();
		this.email = employee.getEmail();
		this.password = employee.getPassword();
		this.createdAt = employee.getCreatedAt();
		this.updatedAt = employee.getUpdatedAt();
		this.tickets = employee.getTickets();
		if (employee.getDepartment() == null) {
			this.department_id = null;
		} else {
			this.department_id = employee.getDepartment().getId();
		}
	}
}
