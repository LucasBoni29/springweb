package br.com.senac.serios.controller;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import br.com.senac.serios.data.domain.repository.LoginRepository;
import br.com.senac.serios.dto.UsuarioDTO;
import br.com.senac.serios.service.LoginService;
import br.com.senac.serios.service.impl.LoginServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Login {

    private final LoginRepository loginRepository;
    private final LoginService service;

    @Autowired
    public Login(LoginRepository loginRepository, LoginServiceImpl loginService) {
        this.loginRepository = loginRepository;
        this.service = loginService;
    }

    @GetMapping("/validando")
    public String exibirMensagem(Model model){
        model.addAttribute("usuario", new UsuarioEntity());
        return "/home";
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("usuario", new UsuarioEntity());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid UsuarioEntity usuario, BindingResult result, RedirectAttributes attributes, UsuarioDTO dto){

        if(result.hasErrors()){
            return "/home";
        }else if(service.validaLoginUsuario(dto.getEmail(), dto.getSenha())){
            return "/list";
        }
        attributes.addFlashAttribute("mensagem", "E-mail ou senha inválidos");
        return "redirect:/validando";
    }

}
