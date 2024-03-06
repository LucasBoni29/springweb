package br.com.senac.serios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/usuario")
public class Lista {
   

    @GetMapping("/lista")
    public String home(){
        return "index";
    }

}
