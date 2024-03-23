package br.com.senac.serios.controller;

import br.com.senac.serios.dto.UsuarioDTO;
import br.com.senac.serios.service.UsuariosService;
import br.com.senac.serios.service.impl.UsuariosServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuariosService service;

    @Autowired
    public UsuarioController(UsuariosServiceImpl listarUsuariosService){
        this.service = listarUsuariosService;
    }

    @GetMapping("/listar")
    public ModelAndView listarUsuarios(@RequestParam(value = "filtro", required = false) String nome){
        ModelAndView modelAndView = new ModelAndView("index");
        List<UsuarioDTO> listaUsuario = service.listarUsuarios(nome);

        modelAndView.addObject("usuarios", listaUsuario);

        modelAndView.addObject("usuariosForm", new UsuarioDTO());

        modelAndView.addObject("usuarioDtoEdit", new UsuarioDTO());

        modelAndView.addObject("usuario", new UsuarioDTO());
        return modelAndView;
    }

    @PostMapping("/desativar/{id}")
    public String desativarUsuario(@PathVariable Long id) {
        service.alterarStatusUsuario(id);
        return "redirect:/usuario/listar";
    }

    @GetMapping("/index")
    public String index(Model model){
        return "index";
    }

    @PostMapping("/cadastrar")
    public String cadastrarUsuario(@Valid @ModelAttribute("usuariosForm") UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes attributes){
        String caminho = service.cadastrarUsuario(usuarioDTO, result, attributes);
        return "redirect:/usuario/"+caminho;
    }

    @PostMapping("/alterar/{id}")
    public String alterarUsuario(@PathVariable Long id, HttpSession session,
                                 @Valid @ModelAttribute("usuarioDtoEdit") UsuarioDTO usuarioDTO, BindingResult result,
                                 RedirectAttributes attributes){
        String caminho = service.alterarUsuario(id, session, usuarioDTO, attributes, result);
        return "redirect:/usuario/"+caminho;
    }
}