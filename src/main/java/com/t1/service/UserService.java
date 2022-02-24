package com.t1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t1.entity.PokemonEntity;
import com.t1.entity.PokemonTypeEntity;
import com.t1.entity.UserEntity;
import com.t1.repository.PokemonRepository;
import com.t1.repository.PokemonTypeRepository;
import com.t1.repository.UserRepository;
import com.t1.requestedto.CreatePokemonRequest;
import com.t1.requestedto.CreatePokemonTypeRequest;
import com.t1.requestedto.CreateUserRequest;
import com.t1.requestedto.InsertPokemonRequest;
import com.t1.requestedto.UpdateUserRequest;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PokemonRepository pokemonRepository;

	@Autowired
	PokemonTypeRepository pokemonTypeRepository;
	
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
	
	public List<UserEntity> getByUser(String username){
		return userRepository.findByUsername(username);
	}
	

	public UserEntity createUser(CreateUserRequest createUserRequest) {

		UserEntity user = new UserEntity(createUserRequest);

		List<PokemonTypeEntity> pokemonTypes = new ArrayList<PokemonTypeEntity>();
		List<PokemonEntity> userPokemons = new ArrayList<PokemonEntity>();

		if (createUserRequest.getPokemons() != null) {

			for (CreatePokemonRequest createPKM : createUserRequest.getPokemons()) {
				PokemonEntity pokemon = new PokemonEntity();
				if (createPKM.getTypes() != null) {
					for (CreatePokemonTypeRequest createType : createPKM.getTypes()) {
						PokemonTypeEntity tipoPKM = new PokemonTypeEntity();
						tipoPKM.setPkmType(createType.getPkmType());
						tipoPKM.setPkm(pokemon);
						pokemonTypes.add(tipoPKM);
						
					}
					
				}
				
				pokemon.setTypes(pokemonTypes);
				pokemon.setPkmName(createPKM.getPkmName());
				user = userRepository.save(user);
				pokemon.setUser(user);
				userPokemons.add(pokemon);
			}

			pokemonRepository.saveAll(userPokemons);
			pokemonTypeRepository.saveAll(pokemonTypes);
		}
		user.setPkmTeam(userPokemons);

		return user;
	}
	
	public UserEntity insertPokemon(InsertPokemonRequest insertPokemonRequest) {		

		UserEntity user = userRepository.getById(insertPokemonRequest.getId());
		
		List<PokemonTypeEntity> pokemonTypes = new ArrayList<PokemonTypeEntity>();
		List<PokemonEntity> userPokemons = new ArrayList<PokemonEntity>();

		if (insertPokemonRequest.getPokemons() != null) {

			for (CreatePokemonRequest createPKM : insertPokemonRequest.getPokemons()) {
				PokemonEntity pokemon = new PokemonEntity();
				if (createPKM.getTypes() != null) {
					for (CreatePokemonTypeRequest createType : createPKM.getTypes()) {
						PokemonTypeEntity tipoPKM = new PokemonTypeEntity();
						tipoPKM.setPkmType(createType.getPkmType());
						tipoPKM.setPkm(pokemon);
						pokemonTypes.add(tipoPKM);
						
					}
					
				}
				
				pokemon.setTypes(pokemonTypes);
				pokemon.setPkmName(createPKM.getPkmName());
				user = userRepository.save(user);
				pokemon.setUser(user);
				userPokemons.add(pokemon);
			}

			pokemonRepository.saveAll(userPokemons);
			pokemonTypeRepository.saveAll(pokemonTypes);
		}
		
		user.setPkmTeam(userPokemons);

		return userRepository.save(user);
	}
	
	public String deletePokemon(Long id) {
		pokemonRepository.deleteById(id);
		return "Pokemon Deleted";
	}
	
	public UserEntity updateUserDetails(UpdateUserRequest updateUserRequest) {
		UserEntity existingUserDetails = userRepository.getById(updateUserRequest.getId());
		PokemonEntity existingPokemon = pokemonRepository.getById(updateUserRequest.getId());

		List<PokemonTypeEntity> pokemonTypes = new ArrayList<PokemonTypeEntity>();
		List<PokemonEntity> userPokemons = new ArrayList<PokemonEntity>();
		
		if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
		existingUserDetails.setPassword(updateUserRequest.getPassword());
		}

		if (updateUserRequest.getTeamName() != null && !updateUserRequest.getTeamName().isEmpty()) {
		existingUserDetails.setTeamName(updateUserRequest.getTeamName());
		}

		if (updateUserRequest.getTrainerName() != null && !updateUserRequest.getTrainerName().isEmpty()) {
		existingUserDetails.setTrainerName(updateUserRequest.getTrainerName());
		}

		if (updateUserRequest.getRol() != null && !updateUserRequest.getRol().isEmpty()) {
		existingUserDetails.setRol(updateUserRequest.getRol());
		}
			
		return userRepository.save(existingUserDetails);

	}

}
