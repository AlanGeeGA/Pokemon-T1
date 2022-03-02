package com.t1.requestedto;


import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequest {
	
	@NotNull(message = "Team name is required!")
	@NotBlank(message = "Team name is required!")
	private String teamName;
	
	@NotNull(message = "Trainer name is required!")
	@NotBlank(message = "Trainer name is required!")
	private String trainerName;
	
	@NotNull(message = "Rol is required!")
	@NotBlank(message = "Rol is required!")
	private String rol;
	
	@NotNull(message = "Username is required!")
	@NotBlank(message = "Username is required!")
	private String username;
	
	@NotNull(message = "Password is required!")
	@NotBlank(message = "Password is required!")
	private String password;
	
	@NotEmpty(message = "At least 1 pokemon must be registered")
	private List<CreatePokemonRequest> pokemons;
	
}
