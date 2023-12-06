package com.ahk.apigateway.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahk.apigateway.model.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@GetMapping("/login")
	public ResponseEntity<AuthResponse> login(
			@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client,
			@AuthenticationPrincipal OidcUser user,
			Model model
			){
		
		logger.info("user email id {}", user.getEmail());
		
		AuthResponse authResponse = new AuthResponse();
		
		authResponse.setAccessToken(client.getAccessToken().getTokenValue());
		authResponse.setRefereshToken(client.getRefreshToken().getTokenValue());
		authResponse.setUserId(user.getEmail());
		authResponse.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());
		
		List<String> authorities = user.getAuthorities().stream().map(grantedAuthorites -> {
			return grantedAuthorites.getAuthority();
		}
		
				).collect(Collectors.toList());
		authResponse.setAuthorities(authorities);
		
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}
}
