package com.t1.controller;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t1.entity.UserEntity;
import com.t1.repository.UserRepository;
import com.t1.requestedto.CreateUserRequest;
import com.t1.requestedto.UpdateUserRequest;
import com.t1.requestedto.CreatePokemonRequest;
import com.t1.requestedto.CreateUserRequest;
import com.t1.requestedto.DeleteRequest;
import com.t1.requestedto.InsertPokemonRequest;
import com.t1.requestedto.UpdateUserRequest;
import com.t1.responsedto.PokemonResponse;
import com.t1.responsedto.UserResponse;
import com.t1.service.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/create")
	public UserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
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
	public List<UserResponse> getByUser(@PathVariable String username){
		List<UserEntity> userList = userService.getByUser(username);
		List<UserResponse> userResponseList = new ArrayList<UserResponse>();
	
		userList.stream().forEach(user -> {
		userResponseList.add(new UserResponse(user));
		});
	
		return userResponseList;
	}
	
	@DeleteMapping("/delete/{username}")
	public String deletePokemon(@PathVariable String username, @RequestBody DeleteRequest deleteRequest) {
		return userService.deletePokemon(deleteRequest);
	}
	
	@PostMapping("/addPokemon/{username}")
	public UserResponse addPkm(@PathVariable String username, @RequestBody InsertPokemonRequest insertPokemonRequest) {
		UserEntity pkm = userService.insertPokemon(username, insertPokemonRequest);
		
		return new UserResponse(pkm);
	}
	 
	
	@GetMapping("/")
	public String getSaludo() {
		
		return "Hola estoy funcionando";
	}
	
	@PutMapping("/update/{username}")
	public UserResponse updateDetails(@PathVariable String username, @RequestBody UpdateUserRequest updateUserReq) {
		UserEntity updateUser = userService.updateUser(updateUserReq);
		return new UserResponse(updateUser);
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
