package com.projects.app.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.app.commons.constants.ProfilePictureTypeEnum;
import lombok.*;

@Data
public class UserDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private int id;

	@NotEmpty
	@Size(min = 4, message = "Username must be min of 4 characters !!")
	private String name;

	@Email(message = "Email address is not valid !!")
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 chars !!")
	private String password;


	@NotEmpty
	private String about;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String profilePicture;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private ProfilePictureTypeEnum profilePictureType;

	private Set<RoleDto> roles = new HashSet<>();
	
	

}
