package com.t1.requestedto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePokemonRequest {
	
	private String pkmName;
	
	@NotNull
	private String types;

}
