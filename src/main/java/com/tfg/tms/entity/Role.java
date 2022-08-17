package com.tfg.tms.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * This class is used to hold the roles available to users in the system. It is persisted in the database.
 */

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, nullable = false)
	private String code;
	private String name;

	@ManyToMany(mappedBy = "customerRoles")
	private Set<Customer> customers;

	@ManyToMany(mappedBy = "employeeRoles")
	private Set<Employee> employees;

	public Role(String name) {
		this.name = name;
	}
}
