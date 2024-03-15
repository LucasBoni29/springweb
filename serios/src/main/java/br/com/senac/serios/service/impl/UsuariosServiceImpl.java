package br.com.senac.serios.service.impl;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import br.com.senac.serios.data.domain.repository.UsuariosRepository;
import br.com.senac.serios.dto.UsuarioDTO;
import br.com.senac.serios.service.UsuariosService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceImpl implements UsuariosService {


    private final UsuariosRepository usuariosRepository;

    private final ModelMapper modelMapper = new ModelMapper();


    @Autowired
    public UsuariosServiceImpl(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public List<UsuarioDTO> listarUsuarios(String nome) {
        List<UsuarioEntity> listaUsuarios;

        if (nome != null && !nome.isBlank()){
            listaUsuarios = usuariosRepository.findByNomeContaining(nome);
        }else{
            listaUsuarios = usuariosRepository.findAll();
        }
        List<UsuarioDTO> listaUsuariosDTO = new ArrayList<>();

        for (UsuarioEntity usuarioEntity : listaUsuarios) {
            UsuarioDTO usuarioDTO = modelMapper.map(usuarioEntity, UsuarioDTO.class);
            listaUsuariosDTO.add(usuarioDTO);
        }

        return listaUsuariosDTO;
    }

    @Override
    public void alterarStatusUsuario(Long id) {
        Optional<UsuarioEntity> usuario = usuariosRepository.findById(id);

        if (usuario.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com o ID "+id+" não foi encontrado");
        }

        UsuarioEntity usuarioEntity = usuario.get();

        usuarioEntity.setStatus(!usuarioEntity.getStatus());

        usuariosRepository.save(usuarioEntity);
    }

    @Override
    public void cadastrarUsuario() {

    }

    @Override
    public List<String> capturarMensagensErros(BindingResult result) {
        return null;
    }
}
