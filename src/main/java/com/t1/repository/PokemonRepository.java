package com.t1.repository;

import org.springframework.stereotype.Repository;

import com.t1.entity.Composite;
import com.t1.entity.PokemonEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PokemonRepository extends JpaRepository<PokemonEntity, Composite> {

	boolean existsByComposite(String name);
	
}
