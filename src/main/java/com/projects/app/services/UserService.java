package com.projects.app.services;

import java.util.List;
import java.util.Map;

import com.projects.app.dto.UserDto;
import com.projects.app.dto.UserProfileDTO;

public interface UserService {

	UserDto registerNewUser(UserDto user);
	
	
	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);
	UserProfileDTO getProfilePictureUserById(Integer userId);
	UserProfileDTO getUserProfileByUserId(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);

	void createUser(UserProfileDTO userProfileDTO);

	Map<String,String> sendEmailOTP(String email, String emailFor);

	void changePassword(String email, String password);

	UserDto getUserByEmailPassword(String username, String password);
}
