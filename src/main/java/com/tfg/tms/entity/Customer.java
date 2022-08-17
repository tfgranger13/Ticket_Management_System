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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tfg.tms.dto.RegisterDTO;

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
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private String email;

	private String password;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", insertable = false)
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ticket> tickets = new HashSet<Ticket>();

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Message> messages = new HashSet<Message>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> customerRoles = new HashSet<>();

	public Customer(RegisterDTO registerDTO) {
		super();
		this.firstName = registerDTO.getFirstName();
		this.lastName = registerDTO.getLastName();
		this.email = registerDTO.getEmail();
		this.password = registerDTO.getPassword();
	}

	// method to add a role to a user
	public void addCustomerRoles(Role role) {
		customerRoles.add(role);
		role.getCustomers().add(this);
	}

	// method to remove a role from a user
	public void removeCustomerRoles(Role role) {
		customerRoles.remove(role);
		role.getCustomers().remove(this);
	}

}
