package com.t1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.t1.entity.PokemonEntity;
import com.t1.entity.PokemonTypeEntity;
import com.t1.entity.UserEntity;
import com.t1.repository.PokemonRepository;
import com.t1.repository.PokemonTypeRepository;
import com.t1.repository.UserRepository;
import com.t1.requestedto.CreatePokemonRequest;
import com.t1.requestedto.CreatePokemonTypeRequest;
import com.t1.requestedto.CreateUserRequest;
import com.t1.requestedto.UpdateUserRequest;
import com.t1.responsedto.UserResponse;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
<<<<<<< HEAD
	UserResponse userResponse;
	
=======

	@Autowired
	PokemonRepository pokemonRepository;

	@Autowired
	PokemonTypeRepository pokemonTypeRepository;

>>>>>>> a35811213e473ff9796393a9a034ab5a6123cca4
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
	
	public List<UserEntity> getByUser(String username){
		return userRepository.findByUsername(username);
	}
	
<<<<<<< HEAD
	public UserEntity updateUser(UpdateUserRequest updateUserRequest) {
		UserEntity user = userRepository.findById(updateUserRequest.getId()).get();
		
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
			user.setRol(updateUserRequest.getRol());
		} 
		
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
	
	public String deleteStudent (UserEntity user) {
		userRepository.delete(user);
		return "Se ha borrado el usuario";
	}
	
}
=======
>>>>>>> a35811213e473ff9796393a9a034ab5a6123cca4

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

}
