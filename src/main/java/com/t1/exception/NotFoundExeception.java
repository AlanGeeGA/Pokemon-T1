package com.t1.exception;

import java.util.NoSuchElementException;

public class NotFoundExeception extends NoSuchElementException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundExeception(String message) {
		super(message);
	}

}
