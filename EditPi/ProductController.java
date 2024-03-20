package EditPi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Endpoint para listar todos os produtos
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Endpoint para buscar produtos por nome
    @GetMapping("/buscar")
    public List<Product> searchProductsByName(@RequestParam String nome) {
        return productRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Endpoint para editar um produto por código
    @PutMapping("/{codigo}")
    public Product editProduct(@PathVariable int codigo, @RequestBody Product updatedProduct) {
        return productRepository.findById(codigo)
                .map(product -> {
                    product.setNome(updatedProduct.getNome());
                    product.setQuantidade(updatedProduct.getQuantidade());
                    product.setValor(updatedProduct.getValor());
                    product.setStatus(updatedProduct.getStatus());
                    return productRepository.save(product);
                })
                .orElse(null);
    }
}
