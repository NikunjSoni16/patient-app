package com.philips.patientapp.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

import com.philips.patientapp.model.Patient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/patients")
@Api(value="patient app", description="Operations pertaining to Patient App")
public class PatientController {
	
	/*
	 * @Autowired private PatientService patientService;
	 */
	
	private static Map<Integer, Patient> patientRepo = new HashMap<>();
	   static {
		  Patient p1 = new Patient(1, "Nikunj", "Male", null);
	      patientRepo.put(p1.getId(), p1);
	      
	      Patient p2 = new Patient(2, "Geeta", "Female", null);
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
    public Patient showProduct(@PathVariable Integer id, Model model){
		Patient patient  = patientRepo.get(id);
        return patient;
    }
	
	@ApiOperation(value = "Add a patient")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> createProduct(@RequestBody Patient patient) {
		patientRepo.put(patient.getId(), patient);
	    return new ResponseEntity<>("Patient is created successfully", HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Delete a patient")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") String id) { 
		patientRepo.remove(Integer.parseInt(id));
		return new ResponseEntity<>("Patient is deleted successsfully", HttpStatus.OK);
	}
}
