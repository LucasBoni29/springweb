package br.com.senac.serios.dto;

import br.com.senac.serios.data.domain.entity.ImagemProdutoEntity;
import lombok.Getter;
import org.modelmapper.ModelMapper;

@Getter
public class ImagemProdutoDTO {

    // Getters e Setters
    private Long id;
    private String caminho;
    private boolean principal;
    private Long produtoId; // Id do produto associado à imagem

    public ImagemProdutoEntity toEntity() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ImagemProdutoEntity.class);
    }

    public static ImagemProdutoDTO fromEntity(ImagemProdutoEntity imagemProdutoEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(imagemProdutoEntity, ImagemProdutoDTO.class);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }
}
