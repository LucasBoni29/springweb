package br.com.senac.serios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "br.com.senac.serios.data.domain.entity")
public class SeriosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeriosApplication.class, args);
	}

}
