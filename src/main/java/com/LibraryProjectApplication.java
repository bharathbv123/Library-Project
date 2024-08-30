package com;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.qos.logback.classic.Logger;

@SpringBootApplication
public class LibraryProjectApplication implements CommandLineRunner{

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LibraryProjectApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(LibraryProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Application has initialized successfully.");
		
	}

}
