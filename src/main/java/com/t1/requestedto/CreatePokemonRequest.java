package com.t1.requestedto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePokemonRequest {
	
	private String pkmName;

	private String types;

}
