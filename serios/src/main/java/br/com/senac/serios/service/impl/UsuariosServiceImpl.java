package br.com.senac.serios.service.impl;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import br.com.senac.serios.data.domain.repository.UsuariosRepository;
import br.com.senac.serios.dto.UsuarioDTO;
import br.com.senac.serios.service.UsuariosService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceImpl implements UsuariosService {


    private final UsuariosRepository usuariosRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final PasswordEncoder encoder;


    @Autowired
    public UsuariosServiceImpl(UsuariosRepository usuariosRepository, PasswordEncoder encoder) {
        this.usuariosRepository = usuariosRepository;
        this.encoder = encoder;
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
    public String cadastrarUsuario(UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes attributes) {
        boolean temErros = validacaoCampos(usuarioDTO, result, attributes);

        if (temErros){
            List<String> listaErros = capturarMensagensErros(result);
            attributes.addFlashAttribute("erros", "Usuário não cadastrado no sistema");
            attributes.addFlashAttribute("erros", listaErros);
            return "listar";
        }else{
            String senhaCriptografada = encoder.encode(usuarioDTO.getSenha());
            usuarioDTO.setSenha(senhaCriptografada);
            usuarioDTO.setStatus(1);

            salvarUsuario(usuarioDTO);
            attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
            return "listar";
        }
    }

    private boolean validacaoCampos(UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes attributes) {
        if (!isNomeValido(usuarioDTO.getNome()) || usuarioDTO.getNome().length() < 3){
            result.rejectValue("nome", "nome.invalido", "O nome deve conter apenas letras e espaços");
        }

        if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmacaoSenha())) {
            result.rejectValue("confirmacaoSenha", "senha.mismatch", "As senhas digitadas não são iguais.");
        }

        if (existeEmail(usuarioDTO.getEmail())) {
            result.rejectValue("email", "email.exists", "O e-mail já está cadastrado. Por favor, escolha outro.");
        }

        if (result.hasErrors()){
            return true;
        }else{
            return false;
        }
    }

    private void salvarUsuario(UsuarioDTO usuarioDTO) {
        String senhaCriptografada = encoder.encode(usuarioDTO.getSenha());
        usuarioDTO.setSenha(senhaCriptografada);
        usuarioDTO.setStatus(1);

        UsuarioEntity entity = modelMapper.map(usuarioDTO, UsuarioEntity.class);

        usuariosRepository.save(entity);
    }

    private boolean existeEmail(String email) {
        return usuariosRepository.existsByEmail(email);
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

    @Override
    public void alterarUsuario(Long id, HttpSession session, UsuarioDTO usuarioDTO, RedirectAttributes attributes) {

        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isBlank()
                && usuarioDTO.getConfirmacaoSenha() != null && !usuarioDTO.getConfirmacaoSenha().isBlank()){
            confirmarSenha(id, usuarioDTO, attributes);
        }else {
            usuarioDTO.setSenha(null);
        }
    }

    private void confirmarSenha(Long id, UsuarioDTO usuarioDTO, RedirectAttributes attributes) {
        Optional<UsuarioEntity> usuario = usuariosRepository.findById(id);

        if (usuario.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com o ID "+id+" não foi encontrado");
        }

        if (encoder.matches(usuarioDTO.getSenha(), usuario.get().getSenha())){
            usuarioDTO.setSenha(null);
            usuarioDTO.setConfirmacaoSenha(null);
        }
        if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmacaoSenha())){
            attributes.addFlashAttribute("mensagem", "As senhas não coincidem!");
        }
    }

    private boolean isNomeValido(String nome) {
        return StringUtils.hasText(nome) && nome.matches("^[a-zA-Z\\s]+$");
    }
}
