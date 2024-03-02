package br.com.senac.serios.data.domain.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "usuario")
@Data // Anotação para gerar automaticamente getters, setters, toString, equals e hashCode
@EqualsAndHashCode(of = "id") // Gera automaticamente equals e hashCode usando apenas o campo "id"
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    private String nome;

    @Column(nullable = false, unique = true)
    @NotNull
    @Email
    @NotEmpty(message = "O e-mail deve ser informado")
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "A senha deve ser informada")
    private String senha;

    @Column(nullable = false)
    @CPF
    private String cpf;

    @Column(nullable = false)
    private Integer grupo;

    @Column(nullable = false)
    private boolean status;
}