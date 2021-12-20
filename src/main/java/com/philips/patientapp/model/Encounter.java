package com.philips.patientapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Encounter {

	private Long id;
	private String description;
	private String type;
}
