package com.philips.patientapp.model;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Meta {
	public Meta() {
	}
	private List<String> profile = Arrays.asList("https://example.org/fhir/StructureDefinition/AppPatient") ;
}
