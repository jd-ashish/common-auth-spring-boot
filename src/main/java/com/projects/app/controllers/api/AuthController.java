package com.projects.app.controllers.api;

import com.projects.app.repositories.RoleRepo;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.projects.app.exceptions.ApiException;
import com.projects.app.dto.JwtAuthRequest;
import com.projects.app.dto.JwtAuthResponse;
import com.projects.app.dto.UserDto;
import com.projects.app.security.JwtTokenHelper;
import com.projects.app.services.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
@SecurityRequirement(name = "javainuseapi")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setTokenType("Bearer ");
		UserDto user = this.userService.getUserByEmailPassword(request.getUsername(),request.getPassword());
		if(user != null) {
			response.setData(user);
		}
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details !!");
			throw new ApiException("Invalid username or password !!");
		}

	}

	// register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}

	@PostMapping("/send-email-otp/{email}/{emailFor}")
	public ResponseEntity<?> sendEmailOTP(@PathVariable String email, @PathVariable String emailFor){

		Map<String,String> map = this.userService.sendEmailOTP(email, emailFor);
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody JwtAuthRequest request){

		this.userService.changePassword(request.getUsername(), request.getPassword());
		return new ResponseEntity<>("Successfully update password! ", HttpStatus.CREATED);
	}





}
