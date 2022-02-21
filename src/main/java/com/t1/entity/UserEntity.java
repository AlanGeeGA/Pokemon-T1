
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.t1.requestedto.CreateUserRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity

import lombok.Data;


@Table(name="users")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@Column(name="team_name", unique=true)
	private String teamName;
	
	@Column(name="trainer_name", unique=true)
	private String trainerName;
	
	@Column(name="rol")
	private String rol;
	
	@Column(name="username", unique=true)
	private String username;
	
	@Column(name="user_password")
	private String password;
	
	public UserEntity(CreateUserRequest createUserRequest) {
		this.teamName = createUserRequest.getTeamName();
		this.trainerName = createUserRequest.getTrainerName();
		this.rol = createUserRequest.getRol();
		this.username = createUserRequest.getUsername();
		this.password = createUserRequest.getPassword();
	}
	
}
