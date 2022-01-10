package com.philips.patientapp.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Patient {

	private Integer id;
	private String name;
	private String family;
	private String gender;
	private Date dateOfBirth;
	private List<Encounter> encounters;
}
