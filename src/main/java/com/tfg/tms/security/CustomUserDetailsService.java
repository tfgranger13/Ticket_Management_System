package com.tfg.tms.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tfg.tms.dao.CustomerRepository;
import com.tfg.tms.entity.Customer;
import com.tfg.tms.entity.Role;

/*
 * This file is from the JavaDevJournal guide found at https://www.javadevjournal.com/spring-security-tutorial/ 
 * 
 * This class implements the UserDetailService to assist in verification of customer login credentials.
 * It will create a User object based on the information in the database.
 * This object can be compared to the credentials entered in the login form to verify the user.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository repo;

	// grab the customer from the database and create the user object with the email
	// and password
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = repo.findByEmail(email);
		// if the customer doesn't exist, throw an exception
		if (customer == null) {
			// TODO: check to see if the person logging in is an employee?
			throw new UsernameNotFoundException("Customer Not Found");
		}

		// build a user object from the CustomUser class (found in the Security package)
		// @formatter:off
		CustomUser user = CustomUser.CustomUserBuilder.aCustomUser()
				.withUsername(customer.getEmail())
				.withPassword(customer.getPassword())
				.withAuthorities(getAuthorities(customer))
				/* these features are not being implemented in my site but it's interesting to see how they function */
				/* .withEnabled(customer.isLoginDisabled()) */
				/* .withSecret(customer.getSecret()) */
				/* .withAccountNonLocked(false) */
				.build();

		return user;
		// @formatter:on

	}

	// get the authorities (roles) for the user
	private Collection<GrantedAuthority> getAuthorities(Customer customer) {
		Set<Role> userRoles = customer.getCustomerRoles();
		Collection<GrantedAuthority> authorities = new ArrayList<>(userRoles.size());
		for (Role userRole : userRoles) {
			// MySQL doesn't like upper case, so the roles are stored as lower case
			// .toUpperCase() used so roles can be recognized by Spring Security
			authorities.add(new SimpleGrantedAuthority(userRole.getCode().toUpperCase()));
		}

		return authorities;
	}

}
