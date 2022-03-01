package com.t1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part.IgnoreCaseType;
import org.springframework.stereotype.Service;

import com.t1.entity.Composite;
import com.t1.entity.PokemonEntity;
import com.t1.entity.UserEntity;
import com.t1.exception.PokemonLimitException;
import com.t1.exception.RolCantChange;
import com.t1.repository.PokemonRepository;
import com.t1.repository.UserRepository;
import com.t1.requestedto.CreatePokemonRequest;
import com.t1.requestedto.CreateUserRequest;

import com.t1.requestedto.UpdateUserRequest;
import com.t1.responsedto.UserResponse;

import com.t1.requestedto.DeleteRequest;
import com.t1.requestedto.InsertPokemonRequest;
import com.t1.requestedto.UpdateUserRequest;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PokemonRepository pokemonRepository;
	
	public UserEntity createUser(CreateUserRequest createUserRequest) {

		UserEntity user = new UserEntity(createUserRequest);
		
		
		if(user.getTeamName() == null || user.getTeamName().isEmpty() || 
				user.getTrainerName() == null || user.getTrainerName().isEmpty() ||
				user.getRol() == null || user.getRol().isEmpty() || 
				user.getUsername() == null || user.getUsername().isEmpty() || 
				user.getPassword() == null || user.getPassword().isEmpty()) {
			throw new NullPointerException();
		}	
		
		user.setPkmTeam(new ArrayList<PokemonEntity>());

		if (createUserRequest.getPokemons() != null) {
			for(CreatePokemonRequest createPkm : createUserRequest.getPokemons()) {
				PokemonEntity pokemon = new PokemonEntity();
			
				Composite composite = new Composite(); 
				composite.setUsername(createUserRequest.getUsername());
				composite.setPkmName(createPkm.getPkmName());
				
				pokemon.setComposite(composite);
				pokemon.setType(createPkm.getTypes());
				
				user.getPkmTeam().add(pokemon);
				
				//no más de 10
				String userRol= user.getRol();//
				int numpokemons= user.getPkmTeam().size();//
				//no más de 10
				if (userRol.equalsIgnoreCase("Administrator") && numpokemons >10 ) {
					throw new PokemonLimitException("Admin role can only have a maximum of 10 pokemon");
					}
					
			//
			
			}	
			
	 
			
		 
			
			userRepository.save(user);
		}

		return user;
	}

	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
	
	public List<UserEntity> getByUser(String username){
		return userRepository.findByUsername(username);
	}
	
	public String deletePokemon(DeleteRequest deleteRequest) {
		Composite composite = new Composite(); 
		
		composite.setUsername(deleteRequest.getUsername());
		composite.setPkmName(deleteRequest.getPkmName());
		
		pokemonRepository.deleteById(composite);
		
		return "Pokemon Deleted";
	}
	
	public UserEntity insertPokemon(String username, InsertPokemonRequest insertPokemonRequest) {
				
		UserEntity user = userRepository.getByUsername(username); 
		
	String userRol= user.getRol();//
	int numpokemons= user.getPkmTeam().size();//

	


		if (insertPokemonRequest.getPokemons() != null && !insertPokemonRequest.getPokemons().isEmpty()) {
			for(CreatePokemonRequest createPkm : insertPokemonRequest.getPokemons()) {
				PokemonEntity pokemon = new PokemonEntity();
				Composite composite = new Composite(); 
				
				//no más de 10
					if (userRol.equalsIgnoreCase("Administrator") && numpokemons >10 ) {
						throw new PokemonLimitException("Admin role can only have a maximum of 10 pokemon");
						}
						
				//
					
				composite.setUsername(user.getUsername());
				composite.setPkmName(createPkm.getPkmName());
				
				pokemon.setComposite(composite);
				pokemon.setType(createPkm.getTypes());
				
				user.getPkmTeam().add(pokemon);
			}	
			
			userRepository.save(user);
		}

		return user;
	}

	
	
	public UserEntity updateUser(UpdateUserRequest updateUserRequest) {
		UserEntity user = userRepository.getByUsername(updateUserRequest.getUsername());
				
		
		if(updateUserRequest.getTeamName() != null &&
				!updateUserRequest.getTeamName().isEmpty()) {
			user.setTeamName(updateUserRequest.getTeamName());
		} 
		
		if (updateUserRequest.getTrainerName() !=null && 
				! updateUserRequest.getTrainerName().isEmpty()) {
			user.setTrainerName(updateUserRequest.getTrainerName());
		} 
		
		if(updateUserRequest.getRol() != null &&
				! updateUserRequest.getRol().isEmpty()) {
			throw new RolCantChange("Changing role is not allowed");
		} 
		/*
		if(updateUserRequest.getRol() != null &&
				! updateUserRequest.getRol().isEmpty()) {
			user.setRol(updateUserRequest.getRol());
		} */

		
		if (updateUserRequest.getUsername() != null &&
				! updateUserRequest.getUsername().isEmpty()) {
			user.setUsername(updateUserRequest.getUsername());
		} 
		
		if (updateUserRequest.getPassword() != null &&
				! updateUserRequest.getPassword().isEmpty()) {
			user.setPassword(updateUserRequest.getPassword());
		}
		
		user = userRepository.save(user);
		return user;
	}
	
	//
	
	

}
