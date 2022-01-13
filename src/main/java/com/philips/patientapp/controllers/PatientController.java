package com.philips.patientapp.controllers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.philips.patientapp.custom.exception.PatientNotFoundException;
import com.philips.patientapp.model.Identifier;
import com.philips.patientapp.model.Meta;
import com.philips.patientapp.model.Name;
import com.philips.patientapp.model.Patient;
import com.philips.patientapp.model.PatientFHIR;
import com.philips.patientapp.model.Text;
import com.philips.patientapp.service.PatientProfileValidatorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/patients")
@Api(value="patient app", description="Operations pertaining to Patient App")
public class PatientController {
	
	@Autowired
	private PatientProfileValidatorService patientProfileValidatorService;
	
	private static Map<Integer, Patient> patientRepo = new HashMap<>();
	   static {
		  Patient p1 = new Patient(1, "Nikunj", "Soni", "Male", new Date(), null); 
	      patientRepo.put(p1.getId(), p1);
	      
	      Patient p2 = new Patient(2, "Geeta", "Patel", "Female", new Date(), null); 
	      patientRepo.put(p2.getId(), p2);
	}

	@GetMapping("/")
	 public String welcomeToApp() {
        return "WelCome to Patient App";
    }
	 
	@GetMapping("/patient-app")
	 public String welcomeToPatientApp() {
       return "WelCome to Patient App";
    }
	
	@ApiOperation(value = "Patient list", response = Iterable.class)
	@ApiResponses (value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> patient() {
		return new ResponseEntity<>(patientRepo.values(), HttpStatus.OK);
    }
	
	@ApiOperation(value = "Search a patient with an ID",response = Patient.class)
    @RequestMapping(value = "/{id}", method= RequestMethod.GET, produces = "application/json")
    public Patient searchPatientbyId(@PathVariable Integer id, Model model){
		Patient patient  = patientRepo.get(id);
		if (patient == null)
		      throw new PatientNotFoundException("id-" + id);
        return patient;
    }
	
	@ApiOperation(value = "Add a patient")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> createPatient(@RequestBody Patient patient) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			PatientFHIR fhirPatient = PatientFHIR.builder()
					.resourceType("Patient")
					.id(String.valueOf(patient.getId()))
					.text(new Text("generated", "<div xmlns=\"http://www.w3.org/1999/xhtml\">This is a Patient Resources</div>")) 
					.meta(new Meta())
					.name(Arrays.asList(new Name(patient.getFamily(), Arrays.asList(patient.getName()))))
					.gender(patient.getGender())
					.identifier(Arrays.asList(new Identifier("https://example.org/fhir", "apppatient")))
					.birthDate(sdf.parse(sdf.format(patient.getDateOfBirth())))
					.build();
		
		    // convert book object to JSON
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		    String patientJson = gson.toJson(fhirPatient);
		    // print JSON string
		    System.out.println(patientJson);
		    if (patientProfileValidatorService.validateProfile(patientJson)) {
			    patientRepo.put(patient.getId(), patient);
			    return new ResponseEntity<>("Patient is created successfully", HttpStatus.CREATED);
		    } else {
		    	return new ResponseEntity<>("Resource does not meet the AppPatient profile constrains", HttpStatus.PRECONDITION_FAILED);
		    }
		} catch (Exception ex) {
		    ex.printStackTrace();
		    return new ResponseEntity<>("Patient is not created successfully", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@ApiOperation(value = "Delete a patient")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") String id) { 
		patientRepo.remove(Integer.parseInt(id));
		return new ResponseEntity<>("Patient is deleted successsfully", HttpStatus.OK);
	}
}
