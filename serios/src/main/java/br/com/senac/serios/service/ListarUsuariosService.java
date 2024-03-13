package br.com.senac.serios.service;

import br.com.senac.serios.dto.UsuarioDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface ListarUsuariosService {

    List<UsuarioDTO> listarUsuarios(RedirectAttributes attributes);
}
