package com.philips.patientapp.auth.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {
	
	private final String jwt;
}
