package com.philips.patientapp.utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

import com.google.common.io.Files;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GitHubJWTTokenGenrator {
	
	PrivateKey get(String filename) throws Exception {
		
		URL url = this.getClass()
	            .getClassLoader()
	            .getResource(filename);
	        
	        if(url == null) {
	            throw new IllegalArgumentException(filename + " is not found 1");
	        }
        
        
        
		  byte[] keyBytes = Files.toByteArray(new File(url.getFile()));

		  PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		  KeyFactory kf = KeyFactory.getInstance("RSA");
		  return kf.generatePrivate(spec);
	}

	static String createJWT(String githubAppId, long ttlMillis) throws Exception {
	  //The JWT signature algorithm we will be using to sign the token
	  SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

	  long nowMillis = System.currentTimeMillis();
	  Date now = new Date(nowMillis);

	  //We will sign our JWT with our private key
	  Key signingKey = new GitHubJWTTokenGenrator().get("certs/sample-github-app-1.2022-04-17.private-key.der");

	  //Let's set the JWT Claims
	  JwtBuilder builder = Jwts.builder()
	          .setIssuedAt(now)
	          .setIssuer(githubAppId)
	          .signWith(signatureAlgorithm, signingKey);

	  //if it has been specified, let's add the expiration
	  if (ttlMillis > 0) {
	    long expMillis = nowMillis + ttlMillis;
	    Date exp = new Date(expMillis);
	    builder.setExpiration(exp);
	  }

	  //Builds the JWT and serializes it to a compact, URL-safe string
	  return builder.compact();
	}

	public static void main(String[] args) throws Exception {
	  String jwtToken = createJWT("191815", 600000); //sdk-github-api-app-test
	  //GitHub gitHubApp = new GitHubBuilder().withJwtToken(jwtToken).build();
	  
	  System.out.println("JWT TOKEN: "+ jwtToken);
	}

}
