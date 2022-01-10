package com.philips.patientapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Identifier {

	private String system;
	private String value;
}
