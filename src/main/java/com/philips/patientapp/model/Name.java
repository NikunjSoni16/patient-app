package com.philips.patientapp.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Name {
	private String family;
	private List<String> given;

}
