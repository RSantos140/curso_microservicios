package com.rsantosp.app.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	
	@Bean("clienteRest")
	@LoadBalanced // activa el balanceo de carga 
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
}
