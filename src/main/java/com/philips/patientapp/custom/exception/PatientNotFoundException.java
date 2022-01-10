package com.philips.patientapp.custom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {

	public PatientNotFoundException(String exception) {
		super(exception);
	}
}
