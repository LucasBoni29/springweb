package br.com.senac.serios.data.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produto")
@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "avaliacao", nullable = false)
    private double avaliacao;

    @Column(name = "descricao_detalhada", length = 2000)
    private String descricaoDetalhada;
    
    @Column(name = "precoproduto", precision = 10, scale = 2, nullable = false)
    private BigDecimal precoProduto;
    
    @Column(name = "qtd_estoque", nullable = false)
    private int qtdEstoque;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "imagemPrincipal", nullable = false)
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagemProdutoEntity> imagens = new ArrayList<>();

    public void adicionarImagem(ImagemProdutoEntity imagem) {
        imagens.add(imagem);
        imagem.setProduto(this);
    }

    public void removerImagem(ImagemProdutoEntity imagem) {
        imagens.remove(imagem);
        imagem.setProduto(null);
    }

}
