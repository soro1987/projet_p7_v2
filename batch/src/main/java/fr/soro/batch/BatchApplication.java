package fr.soro.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

@EnableScheduling

public class BatchApplication {

	

	
	
	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

	 @Bean
	 public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	 }
	
	
}
