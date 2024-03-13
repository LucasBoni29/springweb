package br.com.senac.serios.service;

import br.com.senac.serios.dto.UsuarioDTO;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface LoginService {

    String executarLogin(String senhaDescriptografada, UsuarioDTO usuarioDTO, RedirectAttributes attributes,
                         Model model) throws NoSuchAlgorithmException;

    List<String> capturarMensagensErros(BindingResult result);
}
