package br.com.senac.serios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackages = "br.com.senac.serios.data.domain.entity")
public class SeriosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeriosApplication.class, args);
	}

	@Bean
	public PasswordEncoder getPassWordEncoder(){
        return new BCryptPasswordEncoder();
	}

}
