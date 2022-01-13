Introduction to Patient App
===========================

1) Set up JPA FHIR server to host you profiles 
	- Got to git project https://github.com/hapifhir/hapi-fhir-jpaserver-starter
	- $ docker pull hapiproject/hapi:latest
    - $ docker run -p 8080:8080 hapiproject/hapi:latest
	- Above commands will get the docker image to local and start the JPA FHIR Server on 8080 port
	
2) Used profile and resource are placed in "src\main\resources\fhir-resources" folder.

3) Postman collection for testing the whole application
   is present at "src\main\resources\fhir-resources\Patient-app.postman_collection.json".
   
   How to use:
   1) Post the StructureDefinition of created profile to JPA HAPI fhir server.
   2) Get the all the availbale StructureDefinition to confirm the new profile is present there.
   3) To validate the resource against the created profile, 
   	  resource's meta -> profile value should match the canonical URL from generated profile. 
   4) Use Authentication API to generate jwt token.
   5) Try API endpoints with jwt token.     

4) Swagger UI URL http://localhost:8081/swagger-ui.html#/ 
