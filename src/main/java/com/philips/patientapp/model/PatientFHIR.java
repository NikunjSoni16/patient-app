package com.philips.patientapp.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PatientFHIR {

	private String resourceType;
	private String id;
	private Text text;
	private Meta meta;
	private List<Name> name;
	private String gender;
	private List<Identifier> identifier;
	private Date birthDate;
}
