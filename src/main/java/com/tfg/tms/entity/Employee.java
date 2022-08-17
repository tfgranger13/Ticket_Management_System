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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tfg.tms.dto.AdminEmployeeDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * This class represents the employees registered on the site. They are persisted in the database
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(unique = true)
	private String email;

	private String password;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", insertable = false)
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ticket> tickets = new HashSet<Ticket>();

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Message> messages = new HashSet<Message>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "employee_role", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> employeeRoles = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "dept_id")
	private Department department;

	public Employee(AdminEmployeeDTO binder) {
		this.id = binder.getId();
		this.firstName = binder.getFirstName();
		this.lastName = binder.getLastName();
		this.email = binder.getEmail();
		this.password = binder.getPassword();
		this.createdAt = binder.getCreatedAt();
		this.updatedAt = binder.getUpdatedAt();
		this.tickets = binder.getTickets();
	}

	// method to add a role to a user
	public void addEmployeeRoles(Role role) {
		employeeRoles.add(role);
		role.getEmployees().add(this);
	}

	// method to remove a role from a user
	public void removeEmployeeRoles(Role role) {
		employeeRoles.remove(role);
		role.getEmployees().remove(this);
	}
}
