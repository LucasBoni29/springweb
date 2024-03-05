package br.com.senac.serios.data.domain.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
public class LoginEntity {

    @NotNull
    @Email
    @NotEmpty(message = "O e-mail deve ser informado")
    private String email;

    @NotEmpty(message = "A senha deve ser informada")
    private String senha;
}
