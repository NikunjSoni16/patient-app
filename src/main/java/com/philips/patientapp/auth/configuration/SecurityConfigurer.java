package com.philips.patientapp.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.philips.patientapp.auth.filters.JwtRequestFilter;
import com.philips.patientapp.auth.service.AppUserDetailService;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AppUserDetailService userDetailService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	
	
	// Connect sql or H2 DB to authenticate user 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService);
	}
	
	private static final String[] AUTH_WHITELIST = {
	        "/swagger-resources/**",
	        "/swagger-ui.html**",
	        "/v2/api-docs",
	        "/webjars/**"
	};
	
	// To Authorization 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				/*
				 * .antMatchers("/admin").hasRole("ADMIN")
				 * .antMatchers("/user").hasAnyRole("ADMIN", "USER")
				 */
			.antMatchers("/", "/swagger-ui.html**", "/swagger-resources/**", "/v2/api-docs/**", "favicon.ico", "/webjars/**").permitAll()
			.antMatchers("/authenticate").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	// To Pwd to allow as pain text in Spring security framework, Not PROD read 
	@Bean
	public PasswordEncoder getPasswordEncoder() { return NoOpPasswordEncoder.getInstance();};
}
