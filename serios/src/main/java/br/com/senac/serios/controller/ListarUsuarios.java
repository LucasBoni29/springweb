package br.com.senac.serios.controller;

import br.com.senac.serios.dto.UsuarioDTO;
import br.com.senac.serios.service.ListarUsuariosService;
import br.com.senac.serios.service.impl.ListarUsuariosServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class ListarUsuarios {

    private final ListarUsuariosService service;

    @Autowired
    public ListarUsuarios(ListarUsuariosServiceImpl listarUsuariosService){
        this.service = listarUsuariosService;
    }



    @GetMapping("/listar")
    public String listarUsuarios(RedirectAttributes attributes){
        List<UsuarioDTO> listaUsuario = service.listarUsuarios(attributes);

        attributes.addFlashAttribute("usuarios", listaUsuario);
        return "index";
    }
}
