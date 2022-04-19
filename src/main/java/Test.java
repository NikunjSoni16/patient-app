import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.philips.patientapp.model.Identifier;
import com.philips.patientapp.model.Meta;
import com.philips.patientapp.model.Name;
import com.philips.patientapp.model.PatientFHIR;

public class Test {
	public static void main(String[] args) {
		try {
					
					PatientFHIR fhirPatient = PatientFHIR.builder()
							.resourceType("Patient")
							.id("123")
							.meta(new Meta())
							.name(Arrays.asList(new Name("Soni", Arrays.asList("Soni"))))
							.gender("Male")
							.identifier(Arrays.asList(new Identifier("https://example.org/fhir", "apppatient")))
							.birthDate(new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-18"))
							.build();
				
					/*
					 * String input = "2017-01-18";
					 * 
					 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); Date date =
					 * sdf.parse(input);
					 */
				
				    // convert book object to JSON
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				    String json = gson.toJson(fhirPatient);
		
				    // print JSON string
				    System.out.println(json);
		
				} catch (Exception ex) {
				    ex.printStackTrace();
				}
				
			}
}
