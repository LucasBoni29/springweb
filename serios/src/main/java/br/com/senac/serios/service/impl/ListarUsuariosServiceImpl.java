package br.com.senac.serios.service.impl;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import br.com.senac.serios.data.domain.repository.ListarUsuariosRepository;
import br.com.senac.serios.dto.UsuarioDTO;
import br.com.senac.serios.service.ListarUsuariosService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarUsuariosServiceImpl implements ListarUsuariosService {


    private final ListarUsuariosRepository listarUsuariosRepository;

    @Autowired
    public ListarUsuariosServiceImpl(ListarUsuariosRepository listarUsuariosRepository) {
        this.listarUsuariosRepository = listarUsuariosRepository;
    }

    @Override
    public List<UsuarioDTO> listarUsuarios(RedirectAttributes attributes) {
        ModelMapper modelMapper = new ModelMapper();
        List<UsuarioEntity> listaUsuarios = listarUsuariosRepository.findAll();

        UsuarioDTO usuarioDTO = modelMapper.map(listaUsuarios, UsuarioDTO.class);

        List<UsuarioDTO> listaUsuariosDTO = listaUsuarios.stream()
                .map(usuarioEntity -> modelMapper.map(listaUsuarios, UsuarioDTO.class))
                .toList();
        return listaUsuariosDTO;
    }
}
