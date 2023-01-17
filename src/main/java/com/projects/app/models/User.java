package com.projects.app.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.projects.app.commons.constants.ProfilePictureTypeEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_name", nullable = false, length = 100)
	private String name;

	@Column(unique = true)
	private String email;

	private String password;

	private String about;

	@Lob
	private String profilePicture;

	@Enumerated(EnumType.STRING)
	private ProfilePictureTypeEnum profilePictureType;


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role"
			, joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.roles.stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
