package com.t1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.t1.filter.JWTFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTFilter jwtFilter;
	
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.csrf()
         .disable()
         .authorizeRequests().antMatchers("/api/authenticate/*", "/api/user/**").permitAll()
         .antMatchers("/api/verify-token/*").permitAll()
         .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
         .anyRequest()
         .authenticated()
         .and()
         .exceptionHandling()
         .authenticationEntryPoint(entryPoint).and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	}


}
