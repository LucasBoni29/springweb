package br.com.senac.serios.dto;

import br.com.senac.serios.data.domain.entity.ProdutoEntity;
import jakarta.validation.Valid;
import net.bytebuddy.matcher.StringMatcher;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoDTO {

    public ProdutoDTO(){

    }

    private Long id;
    private String nome;
    private double avaliacao;
    private String descricaoDetalhada;
    private BigDecimal precoProduto;
    private int qtdEstoque;
    private boolean status;
    private int imagemPrincipal;
    private String imagemPrincipalString;
    @Valid
    private List<ImagemProdutoDTO> imagens;

    public ProdutoEntity toEntity() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ProdutoEntity.class);

    }

    public ProdutoDTO fromEntity(ProdutoEntity produtoEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produtoEntity, ProdutoDTO.class);
    }

    public String getImagemPrincipalString() {
        return imagemPrincipalString;
    }

    public void setImagemPrincipalString(String imagemPrincipalString) {
        this.imagemPrincipalString = imagemPrincipalString;
    }

    public int getImagemPrincipal() {
        return imagemPrincipal;
    }

    // Getters e Setters
    public void setImagemPrincipal(int ImagemPrincipal) {
        this.imagemPrincipal = ImagemPrincipal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }

    public BigDecimal getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(BigDecimal precoProduto) {
        this.precoProduto = precoProduto;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ImagemProdutoDTO> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemProdutoDTO> imagens) {
        this.imagens = imagens;
    }
}
