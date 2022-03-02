package com.t1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.t1.entity.Composite;
import com.t1.entity.PokemonEntity;
import com.t1.entity.UserEntity;
import com.t1.exception.EmailException;
import com.t1.exception.PasswordException;
import com.t1.exception.PokemonLimitException;
import com.t1.exception.RolCantChange;
import com.t1.exception.TeamNameException;
import com.t1.exception.TrainerNameException;
import com.t1.exception.UsernameException;
import com.t1.repository.PokemonRepository;
import com.t1.repository.UserRepository;
import com.t1.requestedto.CreatePokemonRequest;
import com.t1.requestedto.CreateUserRequest;

import com.t1.requestedto.UpdateUserRequest;

import com.t1.requestedto.DeleteRequest;
import com.t1.requestedto.InsertPokemonRequest;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PokemonRepository pokemonRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserEntity createUser(CreateUserRequest request) {

		UserEntity user = new UserEntity(request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		if(user.getTeamName() == null || user.getTeamName().isEmpty() || 
				user.getTrainerName() == null || user.getTrainerName().isEmpty() ||
				user.getRol() == null || user.getRol().isEmpty() || 
				user.getUsername() == null || user.getUsername().isEmpty() || 
				user.getPassword() == null || user.getPassword().isEmpty()) {
			throw new NullPointerException();
		}
		
		if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
			throw new UsernameException();
		}
		
		if (userRepository.existsByTrainerNameIgnoreCase(request.getTrainerName())) {
			throw new TrainerNameException();
		}
		
		if (userRepository.existsByTeamNameIgnoreCase(request.getTeamName())) {
			throw new TeamNameException();
		}
		
		if (!user.getUsername().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new EmailException();
		}
		
		if (!user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@$!%*?&_-])[A-Za-z\\d#$@$!%*?&_-]{8,}$")) {
			throw new PasswordException();
		}
		
		user.setPkmTeam(new ArrayList<PokemonEntity>());

		if (request.getPokemons() != null || !request.getPokemons().isEmpty()) {
			for(CreatePokemonRequest createPkm : request.getPokemons()) {
				PokemonEntity pokemon = new PokemonEntity();
			
				Composite composite = new Composite(); 
				composite.setUsername(request.getUsername());
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
		}else{
			throw new IllegalArgumentException();
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
	
	public UserEntity updateUser(UpdateUserRequest request) {
		UserEntity user = userRepository.getByUsername(request.getUsername());
		
		if (!request.getUsername().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new EmailException();
		}
		
		if(request.getTeamName() != null &&
				!request.getTeamName().isEmpty()) {
			user.setTeamName(request.getTeamName());
		}
		
		if (request.getTrainerName() !=null && 
				!request.getTrainerName().isEmpty()) {
			user.setTrainerName(request.getTrainerName());
		} 

		if(request.getRol() != null &&
				!request.getRol().isEmpty()) {
			throw new RolCantChange("Changing role is not allowed");
		} 
		
		if (request.getUsername() != null &&
				!request.getUsername().isEmpty()) {
			user.setUsername(request.getUsername());
		} 
		
		String password = user.getPassword();
		
		if (request.getPassword().isEmpty() && request.getPassword() != null) {
			user.setPassword(password);
		} else {
			
			if (!request.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@$!%*?&_-])[A-Za-z\\d#$@$!%*?&_-]{8,}$")) {
				throw new PasswordException();
			}
			
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		
		user = userRepository.save(user);
		return user;
	}
	
	public String deleteUser(String username) {
		List<UserEntity> user = userRepository.findByUsername(username);
		
		userRepository.delete(user.get(0));
		
		return "User deleted";
	}
}
