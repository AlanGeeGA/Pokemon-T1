package com.t1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.t1.entity.Composite;
import com.t1.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Composite> {

	List<UserEntity> findByUsername(String username);

	UserEntity getByUsername(String username);
	
	Boolean existsByUsernameIgnoreCase(String username);
	
	Boolean existsByTrainerNameIgnoreCase(String trainerName);
	
	Boolean existsByTeamNameIgnoreCase(String teamName);
	
	
	
}
