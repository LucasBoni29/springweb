package br.com.senac.serios.service.impl;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import br.com.senac.serios.data.domain.repository.LoginRepository;
import br.com.senac.serios.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private LoginRepository repository;

    @Autowired
    public LoginServiceImpl(LoginRepository loginRepository){
        this.repository = loginRepository;
    }

    @Override
    public boolean validaLoginUsuario(String email, String senha) {
        try{
            return repository.existsByEmailAndSenha(email, senha);
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }
}
