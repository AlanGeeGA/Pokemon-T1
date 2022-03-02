package com.t1.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.t1.exception.EmailException;
import com.t1.exception.NotFoundExeception;
import com.t1.exception.NullBlankException;
import com.t1.exception.PasswordException;
import com.t1.exception.PokemonLimitAdminException;
import com.t1.exception.PokemonLimitProv;
import com.t1.exception.RolCantChange;
import com.t1.exception.RoleDoesntExist;
import com.t1.exception.TeamNameException;
import com.t1.exception.TrainerNameException;
import com.t1.exception.UnauthorizeException;
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
	
	@ExceptionHandler(NotFoundExeception.class)
	public ResponseEntity<ResponseDTO<String>> notFoundException(NotFoundExeception notFound){
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
		ErrorDetails errorDetalles = new ErrorDetails("The password must have upper case, numbers, at least 1 special character, no spaces and minimum 8 characters", 1005);
		return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorDetails> nullPoint(NullPointerException e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Missing fields to fill", 1006);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorDetails> nullPkm(IllegalArgumentException noPkm){
		ErrorDetails error = new ErrorDetails("At least 1 pokemon must be registered", 1007);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(RolCantChange.class)
	public ResponseEntity<ErrorDetails> roleCantChange(RolCantChange e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Changing role is not allowed", 1008);
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(PokemonLimitAdminException.class)
	public ResponseEntity<ErrorDetails> pokemonLimitAdmin(PokemonLimitAdminException e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Admin role can only have a maximum of 10 pokemon", 1009);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PokemonLimitProv.class)
	public ResponseEntity<ErrorDetails> pokemonLimitAdmin(PokemonLimitProv e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Provisional role can only have a maximum of 5 pokemon", 1010);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullBlankException.class)
	public ResponseEntity<ErrorDetails> nullBlank(NullBlankException e, WebRequest req){
		ErrorDetails error = new ErrorDetails(e.getMessage(), 1011);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorDetails> dataIntegrityViolation(DataIntegrityViolationException e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Pokemon already exist!", 1012);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<ErrorDetails> invalidDataAccess(InvalidDataAccessApiUsageException e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Pokemon already exist!", 1013);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RoleDoesntExist.class)
	public ResponseEntity<ErrorDetails> roleDoesntExist(RoleDoesntExist e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Role doesnt exist!", 1014);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UnauthorizeException.class)
	public ResponseEntity<ErrorDetails> roleDoesntExist(UnauthorizeException e, WebRequest req){
		ErrorDetails error = new ErrorDetails("Not authorized!", 1015);
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorDetails> wrongUsernamePasswordException(AuthenticationException exception, WebRequest webRequest) {
		ErrorDetails errorDetalles = new ErrorDetails("Wrong credentials", 1016);
		return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
