package EditPi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    public CommandLineRunner demoData(ProductRepository productRepository) {
        return args -> {
            productRepository.save(new Product(1, "Smartphone", 50, 1000.00, "ativo"));
            productRepository.save(new Product(2, "Smart TV", 20, 2000.00, "ativo"));
            productRepository.save(new Product(3, "Smartwatch", 30, 500.00, "ativo"));
            productRepository.save(new Product(4, "Tablet", 10, 800.00, "desativado"));
            productRepository.save(new Product(5, "Smart Speaker", 15, 150.00, "ativo"));
        };
    }
}

