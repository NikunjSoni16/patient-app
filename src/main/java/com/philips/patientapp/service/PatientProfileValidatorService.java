package com.philips.patientapp.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PatientProfileValidatorService {

	private static final String PROFILE_META_URL = "https://example.org/fhir/StructureDefinition/AppPatient";
	
	public boolean validateProfile(String bodyString) {
		boolean isValidate = false;
		try {
		    // Make HAPI Server call to validate resource 
		    WebClient webClient = WebClient.create("http://localhost:8080/fhir/Patient");
		    WebClient.ResponseSpec response = webClient
		    		.post()
		    		.uri(uriBuilder -> uriBuilder.path("/$validate")
		            .queryParam("profile", PROFILE_META_URL)
		            .build())
		    		.contentType(MediaType.APPLICATION_JSON)
		    		.body(BodyInserters.fromValue(bodyString))
		    		.retrieve();
		    
		    System.out.println("Client response status code:" +
		    		Optional.of(response.toBodilessEntity().block().getStatusCode()).get());
		    isValidate = response.toBodilessEntity().block().getStatusCode().equals(HttpStatus.OK);
		} catch (Exception ex) {
		    ex.printStackTrace();
		} finally {
			return isValidate;
		}
	}
	
}
