package com.oauth2.google;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackages="com.oauth2.google.repository")
public class Oauth2GoogleApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2GoogleApplication.class, args);
	}

}
