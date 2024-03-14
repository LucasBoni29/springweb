package br.com.senac.serios.service.impl;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import br.com.senac.serios.data.domain.repository.LoginRepository;
import br.com.senac.serios.dto.UsuarioDTO;
import br.com.senac.serios.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    private final LoginRepository repository;

    private final PasswordEncoder encoder;


    @Autowired
    public LoginServiceImpl(LoginRepository loginRepository, PasswordEncoder encoder){
        this.repository = loginRepository;
        this.encoder = encoder;
    }

    @Override
    public String executarLogin(String senhaDescriptografada, UsuarioEntity usuarioEntity, RedirectAttributes attributes,
                                Model model) {
        try{
            usuarioEntity = repository.findByEmailIgnoreCase(usuarioEntity.getEmail());

            if (usuarioEntity != null && encoder.matches(senhaDescriptografada, usuarioEntity.getSenha())){
                if (usuarioEntity.getGrupo().equals("ADMINISTRADOR")){
                    attributes.addFlashAttribute("grupo", "ADMINISTRADOR");
                } else if (usuarioEntity.getGrupo().equals("ESTOQUISTA")) {
                    attributes.addFlashAttribute("grupo", "ESTOQUISTA");
                }
                return "redirect:/pagina-principal";
            }else{
                attributes.addFlashAttribute("mensagem", "E-mail ou senha inválidos");
                return "redirect:/home";
            }

        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<String> capturarMensagensErros(BindingResult result) {
        List<String> mensagensErros = new ArrayList<>();

        // Itera sobre todos os erros de campo
        result.getFieldErrors().forEach(erros -> {
            mensagensErros.add(erros.getDefaultMessage());
        });

        return mensagensErros;
    }
}
