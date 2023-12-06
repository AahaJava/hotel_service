package com.ahk.user.service.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import com.ahk.user.service.config.intercepter.RestTemplateInterceptor;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

@Configuration
public class MyConfiguration {
	
	@Autowired
	ClientRegistrationRepository clientRegistrationRepository;
	@Autowired
	OAuth2AuthorizedClientRepository ouAuth2AuthorizedClientRepository;
	
	@Bean
	@LoadBalanced
	RestTemplate getRestTemplate() {
		
		RestTemplate restTemplate = new RestTemplate();
		
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		
		interceptors.add(new RestTemplateInterceptor(manager(clientRegistrationRepository, ouAuth2AuthorizedClientRepository)));
		
		restTemplate.setInterceptors(interceptors);
		
		return restTemplate;
	}

    // declare the bean of OAuth2AuthorizedClientManager
    
    @Bean
    OAuth2AuthorizedClientManager manager(
    		ClientRegistrationRepository clientRegistrationRepository,
    		OAuth2AuthorizedClientRepository ouAuth2AuthorizedClientRepository
    		) {
		
    	OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
    	 
    	DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, ouAuth2AuthorizedClientRepository);
    	defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
    	
    	return defaultOAuth2AuthorizedClientManager;
	}

}
