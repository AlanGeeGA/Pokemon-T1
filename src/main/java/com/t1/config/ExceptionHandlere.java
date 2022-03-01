package com.t1.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.t1.exception.NotFoudExeception;
import com.t1.exception.PokemonLimitException;
import com.t1.exception.RolCantChange;
import com.t1.responsedto.ResponseDTO;

@ControllerAdvice
public class ExceptionHandlere  extends ResponseEntityExceptionHandler{

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String, String> errores = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error->{
			String fieldName= ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errores.put(fieldName, message);
		});
		return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NotFoudExeception.class)
	public ResponseEntity<ResponseDTO<String>> notFoundException(NotFoudExeception notFound){
		ResponseDTO<String> response = new ResponseDTO<String>("the element wasnt found", notFound.getMessage());
		return new ResponseEntity<ResponseDTO<String>>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDetails> psqlExc(RuntimeException e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Usuario o Pokemon ya existente", 900 );
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorDetails> nullPoint(NullPointerException e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Falta un campo por llenar", 1000);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RolCantChange.class)
	public ResponseEntity<ErrorDetails> nullPoint(RolCantChange e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Changing role is not allowed", 1200);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(PokemonLimitException.class)
	public ResponseEntity<ErrorDetails> nullPoint(PokemonLimitException e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Admin role can only have a maximum of 10 pokemon", 1400);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}
