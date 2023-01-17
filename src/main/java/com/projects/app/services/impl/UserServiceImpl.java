package com.projects.app.services.impl;

import com.projects.app.config.email.EmailDetails;
import com.projects.app.config.email.EmailService;
import com.projects.app.models.User;
import com.projects.app.exceptions.ResourceNotFoundException;
import com.projects.app.dto.UserDto;
import com.projects.app.dto.UserProfileDTO;
import com.projects.app.repositories.RoleRepo;
import com.projects.app.repositories.UserRepo;
import com.projects.app.services.UserService;
import com.projects.app.services.mapper.UserMapper;
import com.projects.app.commons.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired private EmailService emailService;

	@Value("${project.image}")
	private String path;


	@Override
	public UserDto createUser(UserDto userDto) {
		User user = userMapper.toEntity(userDto);
		User savedUser = this.userRepo.save(user);
		return userMapper.toDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = userMapper.toDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
		user.setProfilePicture(user.getProfilePicture());
		return userMapper.toDto(user);
	}

	@Override
	public UserProfileDTO getProfilePictureUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
		UserProfileDTO userProfileDTO = new UserProfileDTO();
		userProfileDTO.setProfilePicture(user.getProfilePicture());
		userProfileDTO.setProfilePictureType(user.getProfilePictureType());

		return userProfileDTO;
	}

	@Override
	public UserProfileDTO getUserProfileByUserId(Integer userId) {
		User users = this.userRepo.findById(userId).get();
		UserProfileDTO userProfileDTO = new UserProfileDTO();
		userProfileDTO.setId(users.getId());
		userProfileDTO.setProfilePicture(users.getProfilePicture());
		userProfileDTO.setProfilePictureType(users.getProfilePictureType());
		return userProfileDTO;
	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		return users.stream().map(user -> userMapper.toDto(user)).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);

	}

	@Override
	public void createUser(UserProfileDTO userProfileDTO) {
		User user = userRepo.findById(userProfileDTO.getId()).get();
		user.setProfilePictureType(userProfileDTO.getProfilePictureType());
		user.setProfilePicture(userProfileDTO.getProfilePicture());
		userRepo.save(user);

	}

	@Override
	public Map<String, String> sendEmailOTP(String email, String emailFor) {


		String otp = Utils.OTP();
		EmailDetails details = new EmailDetails();
		details.setSubject("OTP verification email ");
		details.setMsgBody("Your OTP is: "+ otp +" for "+emailFor);
		details.setRecipient(email);
		Map<String,String> map = new HashMap<>();
		map.put("email", email);
		map.put("otp", otp);
		map.put("region", emailFor);
		emailService.sendSimpleMail(details);
		return map;
	}

	@Override
	public void changePassword(String email, String password) {
		User user = this.userRepo.findByEmail(email).get();
		user.setPassword(passwordEncoder.encode(password));
		userRepo.save(user);
	}

	@Override
	public UserDto getUserByEmailPassword(String username, String password) {
		User user = userRepo.findByEmail(username).get();
		if(passwordEncoder.matches(password,user.getPassword())) {
			return userMapper.toDto(user);
		}else{
			return null;
		}

	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = userMapper.toEntity(userDto);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User newUser = this.userRepo.save(user);
		return userMapper.toDto(newUser);
	}



}

