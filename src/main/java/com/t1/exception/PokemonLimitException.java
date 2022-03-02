package com.t1.exception;

public class PokemonLimitException extends RuntimeException {

	public PokemonLimitException(String message) {
		super(message);
	}
}
