package com.philips.patientapp.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Patient {

	private Integer id;
	private String name;
	private String gender;
	private List<Encounter> encounters;
}
