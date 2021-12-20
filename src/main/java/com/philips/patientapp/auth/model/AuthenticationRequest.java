package com.philips.patientapp.auth.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {

	private String username;
    private String password;
    
    public AuthenticationRequest() {
	}
    
    
}
