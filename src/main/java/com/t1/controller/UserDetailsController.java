package com.t1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.t1.config.JWTUtility;
import com.t1.requestedto.LoginRequestDTO;
import com.t1.responsedto.JWTResponseDTO;
import com.t1.responsedto.ResponseDTO;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api/authenticate")
public class UserDetailsController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostMapping("/")
	public ResponseEntity<ResponseDTO<JWTResponseDTO>> signIn(@Valid @RequestBody LoginRequestDTO userLogin) throws Exception{
		try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    		userLogin.getUsername(),
                    		userLogin.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        
        final UserDetails userDetails
                = userDetailsService.loadUserByUsername(userLogin.getUsername());
        
        //generar token
        final String token =
                jwtUtility.generateToken(userDetails);
        
        
        JWTResponseDTO jwtDto = new JWTResponseDTO(token);
        ResponseDTO<JWTResponseDTO> response = new ResponseDTO<JWTResponseDTO>("the token was successfully generated", jwtDto);
        return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@GetMapping("/")
	public String saludo() {
		return "hola";
	}
}
