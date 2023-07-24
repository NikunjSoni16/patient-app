package com.philips.patientapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GitController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GitController.class);
	
	@GetMapping(value = "/login/oauth2/code/github")
	public void addUser(@RequestParam(name = "code") final String code,
	@RequestParam(name = "state") final String state) {
		System.err.println("Inside the controller");
		LOGGER.info("ENTER_LABEL");
		try {
			System.err.println("Code:" + code);
			System.err.println("State:" + state);
		
			this.getAccessToken(code, state);
			LOGGER.info("EXIT_LABEL");

		} catch (Exception e) {
			LOGGER.info("EXCEPTION_LABEL");
			e.printStackTrace();
		}
	}

	
	public String getAccessToken(String code, String state) {



		LOGGER.info("ENTER_LABEL");



		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		UserToken userToken = new UserToken();
		headers.set("Accept", "application/json");



		HttpEntity<String> entity = new HttpEntity<>(userToken.toString(), headers);



		String clientId = "194887ad7687f99f7ba7";
		String clientSecret = "8fea74752bd072b085f306c5c0fad4a0c4bae836";
		String githubUrl = "https://github.com/login/oauth/access_token?client_id=" + clientId + "&client_secret="
		+ clientSecret + "&code=" + code;



		ResponseEntity<UserToken> responseEntity = restTemplate.postForEntity(githubUrl, entity, UserToken.class);



		UserToken userToken2 = responseEntity.getBody();



		//AuthToken authToken = new AuthToken();
		System.out.println(userToken2.getAccessToken());



		//tokenRepository.save(authToken);



		System.err.println("OAuth Token:" + userToken2.getAccessToken());



		LOGGER.info("EXIT_LABEL");
		return null;
		}
}
