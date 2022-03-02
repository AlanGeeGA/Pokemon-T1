package com.t1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.t1.config.JWTUtility;
import com.t1.entity.UserEntity;
import com.t1.mapper.UserMapper;
import com.t1.repository.UserDetailsRepository;
import com.t1.responsedto.ResponseDTO;
import com.t1.responsedto.UserResponseDTO;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
@RequestMapping("/api/verify-token")
public class GetUserByTokenController {

	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@GetMapping("/")
	public ResponseEntity<ResponseDTO<UserResponseDTO>> getUserByToken(@RequestHeader(name = "Authorization") String token) {
		String user =  jwtUtility.getUsernameFromToken(token.substring(6, token.length()));
		UserEntity userEntity = userDetailsRepository.findByUsername(user);
		UserResponseDTO userDto = userMapper.entityToDto(userEntity); 
		ResponseDTO<UserResponseDTO> response = new ResponseDTO<UserResponseDTO>("the user by the token is", userDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
