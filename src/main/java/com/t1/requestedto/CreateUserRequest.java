package com.t1.requestedto;


import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequest {
	
	@NotBlank(message = "Team name is required!")
	private String teamName;
	@NotBlank(message = "Trainer name is required!")
	private String trainerName;
	@NotBlank(message = "Rol is required!")
	private String rol;
	@NotBlank(message = "Username is required!")
	private String username;
	@NotBlank(message = "Password is required!")
	private String password;
	
	@NotBlank(message = "One pokemon is required!")
	private List<CreatePokemonRequest> pokemons;
	
}
