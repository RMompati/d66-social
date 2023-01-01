package com.eroldmr.d66;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"com.eroldmr.common", "com.eroldmr.d66"})
public class D66SocialApplication {

	public static void main(String[] args) {
		SpringApplication.run(D66SocialApplication.class, args);
	}

}
