package com.tfg.tms.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*
 * This class creates a user object 
 */

public class CustomUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String password;
	private String username;
	private Collection<GrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private String secret;

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
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = null;
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
	 * CLASSCEPTION!!!!! This class will build the user object based on the above
	 * constraints most of these are not utilized in my project, but it's
	 * interesting to see how far you can go with user authentication
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
