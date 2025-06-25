package com.shifterwebapp.shifter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ShifterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShifterApplication.class, args);
	}

}
