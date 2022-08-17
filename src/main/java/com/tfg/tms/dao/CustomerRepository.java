package com.tfg.tms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.tms.entity.Customer;

/*
 * This class is a Spring Data JPA repository which takes advantage of the built in methods
 * to have a streamlined repository without the need of an implementation class
 * allowing easier setup of the rest api
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Customer findByEmail(String email);

}
