package com.example.JournalApplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
		ConfigurableEnvironment environment = context.getEnvironment();
        log.info("Environment = {}", environment.getActiveProfiles()[0]);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
