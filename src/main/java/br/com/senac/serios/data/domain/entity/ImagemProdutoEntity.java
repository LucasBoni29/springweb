package br.com.senac.serios.data.domain.entity;

import br.com.senac.serios.dto.ImagemProdutoDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "imagem_produto")
@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
public class ImagemProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "caminho", nullable = false)
    private String caminho;

    @Column(name = "principal", nullable = false)
    private boolean principal;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoEntity produto;

    // outros atributos e métodos

    public ImagemProdutoDTO toDTO() {
        ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
        imagemProdutoDTO.setId(this.id);
        imagemProdutoDTO.setCaminho(this.caminho);
        imagemProdutoDTO.setPrincipal(this.principal);
        // Não é necessário definir o ProdutoEntity no DTO, a menos que seja necessário
        return imagemProdutoDTO;
    }
}
