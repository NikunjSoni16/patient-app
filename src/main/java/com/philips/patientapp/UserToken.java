package com.philips.patientapp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserToken {

@JsonProperty("access_token")
private String accessToken;



@JsonProperty("scope")
private String scope;

@JsonProperty("token_type")
private String tokenType;
}
