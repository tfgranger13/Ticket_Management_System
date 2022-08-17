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
 * This class implements the UserDetailService to assist in verification of customer login credentials.
 * It will create a User object based on the information in the database.
 * This object can be compared to the credentials entered in the login form to verify the user.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = repo.findByEmail(email);
		if (customer == null) {
			throw new UsernameNotFoundException("Customer Not Found");
		}

		// create the user
		CustomUser user = CustomUser.CustomUserBuilder.aCustomUser().withUsername(customer.getEmail())
				.withPassword(customer.getPassword())
				/* .withEnabled(customer.isLoginDisabled()) */
				.withAuthorities(getAuthorities(customer))
				/* .withSecret(customer.getSecret()) */
				/* .withAccountNonLocked(false) */
				.build();

		return user;

	}

	private Collection<GrantedAuthority> getAuthorities(Customer customer) {
		Set<Role> userRoles = customer.getCustomerRoles();
		Collection<GrantedAuthority> authorities = new ArrayList<>(userRoles.size());
		for (Role userRole : userRoles) {
			authorities.add(new SimpleGrantedAuthority(userRole.getCode().toUpperCase()));
		}

		return authorities;
	}

}
