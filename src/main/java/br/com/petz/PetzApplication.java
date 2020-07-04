package br.com.petz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "br.com.petz")
public class PetzApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetzApplication.class, args);
	}

}
