package com.t1.requestedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

	private String teamName;

	private String trainerName;

	private String rol;
	
	private String username;
	
	private String password;


}
