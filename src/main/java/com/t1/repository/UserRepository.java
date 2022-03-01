package com.t1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.t1.entity.Composite;
import com.t1.entity.PokemonEntity;
import com.t1.entity.UserEntity;
import com.t1.requestedto.UpdateUserRequest;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Composite> {

	List<UserEntity> findByUsername(String username);



	
	
	UserEntity getByUsername(String username);
	
	
	
}
