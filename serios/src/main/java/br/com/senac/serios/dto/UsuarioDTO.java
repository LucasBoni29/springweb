package br.com.senac.serios.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
@ApiModel(description = "Classe usada para retornar os valores do usuario")
public class UsuarioDTO {

    @ApiModelProperty(notes = "ID do usuário")
    private Long id;

    @ApiModelProperty(notes = "Nome do usuário")
    private String nome;

    @ApiModelProperty(notes = "E-mail do usuário")
    private String email;

    @ApiModelProperty(notes = "Senha do usuário")
    private String senha;

    @ApiModelProperty(notes = "CPF do usuário")
    private String cpf;

    @ApiModelProperty(notes = "Indica se o usuário é um estoquista ou administrador")
    private String grupo;

    @ApiModelProperty(notes = "Indica se o usuário está inativo ou ativo")
    private Integer status;
}