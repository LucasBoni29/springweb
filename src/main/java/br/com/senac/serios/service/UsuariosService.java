package br.com.senac.serios.service;

import br.com.senac.serios.dto.UsuarioDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface UsuariosService {

    List<UsuarioDTO> listarUsuarios(String nome);

    void alterarStatusUsuario(Long id);

    String cadastrarUsuario(UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes attributes);

    List<String> capturaMensagensErroResult(BindingResult result);

    String alterarUsuario(Long id, HttpSession session, UsuarioDTO usuarioDTO, RedirectAttributes attributes, BindingResult result);

}
