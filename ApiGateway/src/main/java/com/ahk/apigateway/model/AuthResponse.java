package com.ahk.apigateway.model;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	
	private String userId;
	private String accessToken;
	private String refereshToken;
	private long expireAt;
	private Collection<String> authorities;

}
