package com.tfg.tms.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*
 * This file is from the JavaDevJournal guide found at https://www.javadevjournal.com/spring-security-tutorial/
 * 
 * This class creates an object to compare for authentication
 */

public class CustomUser implements UserDetails {
	private static final long serialVersionUID = 1L;

	// these are the attributes I am mainly concerned with for security in my site
	private String password;
	private String username;
	private Collection<GrantedAuthority> authorities;

	/*
	 * these features are not being implemented in my site, but are required to
	 * implement the UserDetails interface
	 */
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private String secret;

	/*
	 * these are the constructors, but this class is built in the
	 * CustomUserDetailsService using the CustomUserBuilder class found below.
	 */
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
			String secret) {
		this(username, password, true, true, true, true, authorities, secret);
	}

	public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			final String secret) {

		if (((username == null) || "".equals(username)) || (password == null)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.username = username;
		this.password = password;
		this.authorities = null;

		/*
		 * again, these fields are not being implemented in my site but are required to
		 * implement the interface
		 */
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.secret = secret;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void eraseCredentials() {
		password = null;
	}

	public String getSecret() {
		return secret;
	}

	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof CustomUser) {
			return username.equals(((CustomUser) rhs).username);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	/*
	 * CLASSCEPTION!!!!! This class is a builder pattern that will construct the
	 * CustomUser object based on the included methods, but most of these are not
	 * utilized in my project so I only need the withPassword, withUsername, and
	 * withAuthorities methods for now.
	 * 
	 * It's interesting to see how far you can go with user authentication!
	 */

	public static final class CustomUserBuilder {
		private String password;
		private String username;
		private Collection<GrantedAuthority> authorities;
		private boolean accountNonExpired;
		private boolean accountNonLocked;
		private boolean credentialsNonExpired;
		private boolean enabled;
		private String secret;

		private CustomUserBuilder() {
		}

		public static CustomUserBuilder aCustomUser() {
			return new CustomUserBuilder();
		}

		public CustomUserBuilder withPassword(String password) {
			this.password = password;
			return this;
		}

		public CustomUserBuilder withUsername(String username) {
			this.username = username;
			return this;
		}

		public CustomUserBuilder withAuthorities(Collection<GrantedAuthority> authorities) {
			this.authorities = authorities;
			return this;
		}

		public CustomUserBuilder withAccountNonExpired(boolean accountNonExpired) {
			this.accountNonExpired = accountNonExpired;
			return this;
		}

		public CustomUserBuilder withAccountNonLocked(boolean accountNonLocked) {
			this.accountNonLocked = accountNonLocked;
			return this;
		}

		public CustomUserBuilder withCredentialsNonExpired(boolean credentialsNonExpired) {
			this.credentialsNonExpired = credentialsNonExpired;
			return this;
		}

		public CustomUserBuilder withEnabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public CustomUserBuilder withSecret(String secret) {
			this.secret = secret;
			return this;
		}

		public CustomUser build() {
			CustomUser customUser = new CustomUser(username, password, !enabled, !accountNonExpired,
					!credentialsNonExpired, !accountNonLocked, authorities, secret);
			customUser.authorities = this.authorities;
			return customUser;
		}
	}
}
