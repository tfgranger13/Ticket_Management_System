package com.tfg.tms.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
 * This class is used to configure the Spring Security features of the site
 */

@Configuration
@EnableWebSecurity
public class TmsSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	private UserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	// this allows access to the resources and static folders
	// @formatter:off
	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
		/*.antMatchers("/resources/**", "/static/**", "/css/**", "/scripts/**", "/images/**");*/
		.antMatchers("/global/**");
	}
	// formatter:on

	// @formatter:off
	// this is the configuration to block off the customer sections and admin sections from visitors
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			.csrf().disable() // I think I'm ok without disabling csrf, thymeleaf generates the token and my logout is a post form
			.authorizeRequests()
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/global/**","/static/**").permitAll()
			.antMatchers("/admin/**").hasAuthority("ADMIN")
			.antMatchers("/customer/**").hasAnyAuthority("ADMIN", "CUSTOMER")
			.antMatchers("/api/**").hasAnyAuthority("ADMIN", "CUSTOMER")
			.and()
			.formLogin()
			.loginPage("/login").defaultSuccessUrl("/customer/tickets").failureUrl("/login?error=true")
			.permitAll()
			.and()
			.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout")
			.permitAll();

	}
	// @formatter:on

}
