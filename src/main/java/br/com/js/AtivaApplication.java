package br.com.js;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.js.service.StorageService;

@SpringBootApplication
public class AtivaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtivaApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(StorageService fileSystemStorageService) {
		return (args) -> {
			fileSystemStorageService.deleteAll();
			fileSystemStorageService.init();
		};
	}

}
