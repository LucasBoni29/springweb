package br.com.senac.serios.controller;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import br.com.senac.serios.service.LoginService;
import br.com.senac.serios.service.impl.LoginServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class Login {

    private final LoginService service;

    @Autowired
    public Login(LoginServiceImpl loginService) {
        this.service = loginService;
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("login", new UsuarioEntity());
        return "login";
    }

    @GetMapping("/pagina-principal")
    public String paginaPrincipal(){
        return "pagina-principal";
    }

    @PostMapping("/login")
    public String login(@Valid UsuarioEntity usuarioEntity, BindingResult result, RedirectAttributes attributes, Model model) throws NoSuchAlgorithmException {
        if(result.hasErrors()){
            List<String> listaErros = service.capturarMensagensErros(result);
            attributes.addFlashAttribute("mensagem", listaErros);
            return "redirect:/home";
        }else{
            return service.executarLogin(usuarioEntity.getSenha(), usuarioEntity, attributes, model);
        }

    }

}
