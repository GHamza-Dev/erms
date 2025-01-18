package com.hahn.erms;

import com.hahn.erms.devonly.DatabaseInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class ErmsApplication implements CommandLineRunner {

	private final DatabaseInitializer databaseInitializer;

	public ErmsApplication(DatabaseInitializer databaseInitializer) {
		this.databaseInitializer = databaseInitializer;
	}

	public static void main(String[] args) {
		SpringApplication.run(ErmsApplication.class, args);
	}

	@Override
	@Profile("!prod")
	public void run(String... args) throws Exception {
//		databaseInitializer.cleanDatabase();
//		databaseInitializer.run(args);
	}
}
