package start;

 



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource(value = {"application.properties"})
@EnableAutoConfiguration
@ComponentScan(basePackages = {
		"start",
		"controller", 
		"fr.soro.service",
		"fr.soro.security",
		"fr.soro.security.jwt",
		"fr.soro.config",
		"fr.soro.web"
	})
@EntityScan(basePackages = {"fr.soro"})
@EnableJpaRepositories(basePackages = {"fr.soro"})
@SpringBootApplication(scanBasePackages = {
		"fr.soro"
		},exclude= { DataSourceAutoConfiguration.class})
@RestController
//@EnableJpaAuditing
public class ApplicationMain{

	public static void main(String[] args) {
		SpringApplication.run(ApplicationMain.class, args);
	}
	
	 @Bean
	 public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	 }
	 	
}
