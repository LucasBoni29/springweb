package br.com.senac.serios.data.domain.repository;

import br.com.senac.serios.data.domain.entity.ImagemProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemProdutoRepository extends JpaRepository<ImagemProdutoEntity, Long> {
    
    ImagemProdutoEntity save(ImagemProdutoEntity imagem);

    Object findByProdutoIdAndPrincipalTrue(Long id);
}
