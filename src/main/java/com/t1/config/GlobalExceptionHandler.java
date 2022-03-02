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

import com.t1.exception.EmailException;
import com.t1.exception.NotFoudExeception;
import com.t1.exception.PasswordException;
import com.t1.exception.PokemonLimitException;
import com.t1.exception.RolCantChange;
import com.t1.exception.TeamNameException;
import com.t1.exception.TrainerNameException;
import com.t1.exception.UsernameException;
import com.t1.responsedto.ResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

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
		ResponseDTO<String> response = new ResponseDTO<String>("The element wasnt found", notFound.getMessage());
		return new ResponseEntity<ResponseDTO<String>>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmailException.class)
	public ResponseEntity<ErrorDetails> emailException(EmailException exception, WebRequest webRequest) {
		ErrorDetails errorDetalles = new ErrorDetails("Username must be an email", 1000);
		return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UsernameException.class)
	public ResponseEntity<ErrorDetails> usernameException(UsernameException exception, WebRequest webRequest) {
		ErrorDetails errorDetalles = new ErrorDetails("Username already exists", 1002);
		return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(TeamNameException.class)
	public ResponseEntity<ErrorDetails> teamNameException(TeamNameException exception, WebRequest webRequest) {
		ErrorDetails errorDetalles = new ErrorDetails("Team Name already exists", 1003);
		return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(TrainerNameException.class)
	public ResponseEntity<ErrorDetails> trainerNameException(TrainerNameException exception, WebRequest webRequest) {
		ErrorDetails errorDetalles = new ErrorDetails("Trainer Name already exists", 1004);
		return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(PasswordException.class)
	public ResponseEntity<ErrorDetails> passwordException(PasswordException exception, WebRequest webRequest) {
		ErrorDetails errorDetalles = new ErrorDetails("Wrong password format", 1005);
		return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
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
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorDetails> nullPkm(IllegalArgumentException noPkm){
		ErrorDetails error = new ErrorDetails("At least 1 pokemon must be registered", 1100);
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
