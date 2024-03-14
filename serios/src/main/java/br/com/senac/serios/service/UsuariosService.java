package br.com.senac.serios.service;

import br.com.senac.serios.dto.UsuarioDTO;
import jakarta.annotation.Nullable;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UsuariosService {

    List<UsuarioDTO> listarUsuarios(String nome);

    void alterarStatusUsuario(Long id, Integer status);

    void cadastrarUsuario();

    List<String> capturarMensagensErros(BindingResult result);

}
