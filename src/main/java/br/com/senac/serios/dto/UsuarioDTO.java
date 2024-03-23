package br.com.senac.serios.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;

@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
@ApiModel(description = "Classe usada para retornar os valores do usuario")
public class UsuarioDTO {

    @ApiModelProperty(notes = "ID do usuário")
    private Long id;

    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    @ApiModelProperty(notes = "Nome do usuário")
    private String nome;

    @Email
    @ApiModelProperty(notes = "E-mail do usuário")
    private String email;

    @ApiModelProperty(notes = "Senha do usuário")
    private String senha;

    @ApiModelProperty(notes = "Campo de confirmação de senha")
    private String confirmacaoSenha;

    @CPF
    @ApiModelProperty(notes = "CPF do usuário")
    private String cpf;

    @ApiModelProperty(notes = "Indica se o usuário é um estoquista ou administrador")
    private String grupo;

    @ApiModelProperty(notes = "Indica se o usuário está inativo ou ativo")
    private Integer status;
}