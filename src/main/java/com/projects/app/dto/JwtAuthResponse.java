package com.projects.app.dto;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	private String tokenType;
	private Object data;
}
