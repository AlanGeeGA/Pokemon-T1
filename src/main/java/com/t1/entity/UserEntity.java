package com.t1.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.t1.requestedto.CreateUserRequest;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="user_name", length=30, unique=true)
	private String username;

	@Column(name="user_pass", length=30)
	private String password;

	@Column(name="user_teamname", unique=true)
	private String teamName;

	@Column(name="user_trainername", length=30, unique=true)
	private String trainerName;

	@Column(name="user_role")
	private String rol;
	
	@JoinColumn(name="Composite", referencedColumnName="id")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
	private List<PokemonEntity> pkmTeam;
	
	public UserEntity(CreateUserRequest createUserRequest) {
		this.teamName = createUserRequest.getTeamName();
		this.trainerName = createUserRequest.getTrainerName();
		this.rol = createUserRequest.getRol();
		this.username = createUserRequest.getUsername();
		this.password = createUserRequest.getPassword();
	}
	
}
