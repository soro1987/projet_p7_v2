package fr.soro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@Configuration
public class ConfigApp {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public RestTemplate securedRestTemplate(ClientHttpRequestInterceptor customInterceptor) {	
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(customInterceptor);
		return restTemplate;
	}

	public ConfigApp() {
	}

}
