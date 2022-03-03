package com.t1.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.t1.entity.UserEntity;
import com.t1.requestedto.CreateUserRequest;
import com.t1.requestedto.UpdateUserRequest;
import com.t1.requestedto.DeleteRequest;
import com.t1.requestedto.InsertPokemonRequest;
import com.t1.responsedto.UserResponse;
import com.t1.service.UserService;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/create")
	public UserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
		UserEntity user = userService.createUser(createUserRequest);
		
		return new UserResponse(user);
	}
	
	@GetMapping("/getAll")
	public List<UserResponse> getAllUsers(){
		List<UserEntity> userList = userService.getAllUsers();
		List<UserResponse> userResponseList = new ArrayList<UserResponse>();
		
		userList.stream().forEach(user -> {
			userResponseList.add(new UserResponse(user));
		});
		
		return userResponseList;
		
	}

	@GetMapping("/getAllByUser/{username}")
	public List<UserResponse> getByUser(@PathVariable String username) {
		List<UserEntity> userList = userService.getByUser(username);
		List<UserResponse> userResponseList = new ArrayList<UserResponse>();
	
		userList.stream().forEach(user -> {
			userResponseList.add(new UserResponse(user));
		});
	
		return userResponseList;
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/deletePokemon")
	public HttpStatus deletePokemon(@RequestBody DeleteRequest deleteRequest) {
		UserEntity user = userService.getByUser(deleteRequest.getUsername()).get(0);
		userService.deletePokemon(user, deleteRequest);
		return HttpStatus.OK;
	}
	
	@PreAuthorize("isAuthenticated()")  
	@PostMapping("/addPokemon")
	public UserResponse addPkm(@RequestBody InsertPokemonRequest insertPokemonRequest) {
		UserEntity pkm = userService.insertPokemon(getTokenUsername(), insertPokemonRequest);
		
		return new UserResponse(pkm);
	}
	
	@PreAuthorize("isAuthenticated()")  
	@PutMapping("/update")
	public UserResponse updateDetails(@RequestBody UpdateUserRequest updateUserReq) {
		UserEntity updateUser = userService.updateUser(updateUserReq);
		return new UserResponse(updateUser);
	}
	
	@PreAuthorize("isAuthenticated()")  
	@DeleteMapping("/deleteUser")
	public HttpStatus deleteUser() {
		userService.deleteUser(getTokenUsername());
		return HttpStatus.OK;
	}
	
	private String getTokenUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	@GetMapping("/")
	public String getSaludo() {
		
		return "Hola estoy funcionando";
	}
	
	/*@GetMapping("/getAll")
	public List<UserResponse> getAllUsers(){
		List<UserEntity> userList = userService.getAllUsers();
		List<UserResponse> userResponseList = new ArrayList<UserResponse>();
		
		userList.stream().forEach(user -> {
			userResponseList.add(new UserResponse(user));
		});
		
		return userResponseList;
		
	}
	
	@GetMapping("/getAllByUser/{username}")
	public List<UserResponse> getByUser(@PathVariable String username){
		List<UserEntity> userList = userService.getByUser(username);
		List<UserResponse> userResponseList = new ArrayList<UserResponse>();
		
		userList.stream().forEach(user -> {
			userResponseList.add(new UserResponse(user));
		});
		
		return userResponseList;
	}
	
	
	@PostMapping("/create")
	public UserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
		UserEntity user = userService.createUser(createUserRequest);
		
		return new UserResponse(user);
	}
	
	@PostMapping("/insertPokemon/{id}")
	public UserResponse insertPokemon(@RequestBody InsertPokemonRequest insertPokemonRequest) {
		UserEntity user = userService.insertPokemon(insertPokemonRequest);
		
		return new UserResponse(user);
		
	}
	
	@DeleteMapping("/delete")
	public String deletePokemon(@RequestParam long id) {
		return userService.deletePokemon(id);
	}
	
	@PutMapping("/update")
	public UserResponse updateDetails(@RequestBody UpdateUserRequest updateUserReq) {
		UserEntity updateUser = userService.updateUserDetails(updateUserReq);
		return new UserResponse(updateUser);
	}
	
	//@RequestBody UpdatePokemonTypeRequest updatePkmType
	
	@PutMapping("/updatePkm")
	public PokemonResponse updatePokemonDetails(@RequestBody UpdatePokemonRequest updatePkmReq) {
		PokemonEntity updatePokemons = userService.updatePokemonDetails(updatePkmReq);
		return new PokemonResponse(updatePokemons);
	}
	
	/*@PutMapping("/updatePkmType")
	public PokemonTypeResponse updatePokemonTypeDetails(@RequestBody UpdatePokemonTypeRequest updatePkmTypeReq) {
		PokemonTypeEntity updatePokemonsTypes = userService.updatePokemonType(updatePkmTypeReq);
		return new PokemonTypeResponse(updatePokemonsTypes);
	}*/
}
