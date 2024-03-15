package br.com.senac.serios.controller;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import br.com.senac.serios.dto.UsuarioDTO;
import br.com.senac.serios.service.UsuariosService;
import br.com.senac.serios.service.impl.UsuariosServiceImpl;
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
public class Usuarios {

    private final UsuariosService service;

    @Autowired
    public Usuarios(UsuariosServiceImpl listarUsuariosService){
        this.service = listarUsuariosService;
    }



    @GetMapping("/listar")
    public ModelAndView listarUsuarios(@RequestParam(value = "filtro", required = false) String nome){
        ModelAndView modelAndView = new ModelAndView("index");
        List<UsuarioDTO> listaUsuario = service.listarUsuarios(nome);

        modelAndView.addObject("usuarios", listaUsuario);

        modelAndView.addObject("usuariosForm", new UsuarioEntity());
        return modelAndView;
    }

    @PostMapping("/desativar/{id}")
    public String desativarUsuario(@PathVariable Long id, RedirectAttributes attributes) {
        service.alterarStatusUsuario(id);
        return "redirect:/usuario/listar";
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("usuariosForm", new UsuarioEntity());
        return "index";
    }

    @PostMapping("/cadastrar")
    public String cadastrarUsuario(@Valid UsuarioEntity usuarioEntity, BindingResult result){
        if(result.hasErrors()){
            return "redirect:/index";
        }
        service.cadastrarUsuario();
        return "redirect:/listar";
    }
}
