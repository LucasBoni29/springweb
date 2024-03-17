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

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuariosServiceImpl implements UsuariosService {

    private final UsuariosRepository usuariosRepository;

    public static final String LISTAR = "listar";

    private final ModelMapper modelMapper = new ModelMapper();

    private final PasswordEncoder passwordEncoder;

    private static final String ALERTA_TIPO_ERRO = "erros";
    private static final String ALERTA_TIPO_SUCESSO = "mensagem";
    
    private static final String USUARIO_NAO_ENCONTRADO = "Usuário para edição não encontrado no banco de dados";


    @Autowired
    public UsuariosServiceImpl(UsuariosRepository repository, PasswordEncoder encoder) {
        this.usuariosRepository = repository;
        this.passwordEncoder = encoder;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO);
        }

        UsuarioEntity usuarioEntity = usuario.get();

        usuarioEntity.setStatus(!usuarioEntity.getStatus());

        usuariosRepository.save(usuarioEntity);
    }

    @Override
    public String alterarUsuario(Long id, HttpSession session, UsuarioDTO usuarioDTO, RedirectAttributes attributes,
                                 BindingResult result) {
        List<String> mensagensErro = new ArrayList<>();
        UsuarioDTO dto = editarSenha(id, usuarioDTO, mensagensErro);
        boolean temErros = validacaoCamposAlterar(session, usuarioDTO, mensagensErro);

        if (result.hasErrors()){
            List<String> listaErros = capturaMensagensErroResult(result);
            attributes.addFlashAttribute(ALERTA_TIPO_ERRO, listaErros);
        }else{
            if (temErros){
                mensagensErro.add("Usuário não alterado");
                attributes.addFlashAttribute(ALERTA_TIPO_ERRO, mensagensErro);
            }else{
                UsuarioEntity entity = modelMapper.map(dto, UsuarioEntity.class);

                usuariosRepository.save(entity);
                attributes.addFlashAttribute(ALERTA_TIPO_SUCESSO, "Usuário editado com sucesso");
            }
        }

        return LISTAR;
    }



    private UsuarioDTO editarSenha(Long id, UsuarioDTO usuarioDTO, List<String> mensagensErro) {
        Optional<UsuarioEntity> entity = usuariosRepository.findById(id);
        if (entity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO);
        }

        if (!usuarioDTO.getSenha().isBlank()){
            if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmacaoSenha()) && !usuarioDTO.getConfirmacaoSenha().isBlank()
                    || (!usuarioDTO.getSenha().equals(entity.get().getSenha()))){
                mensagensErro.add("As senhas digitadas não coincidem");
                return usuarioDTO;
            } else if (passwordEncoder.matches(usuarioDTO.getSenha(), entity.get().getSenha())){
                mensagensErro.add("A nova senha não pode ser igual a senha antiga");
                return usuarioDTO;
            }else if (Objects.equals(usuarioDTO.getSenha(), usuarioDTO.getConfirmacaoSenha())){
                usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
                return usuarioDTO;
            }
        }

        usuarioDTO.setSenha(entity.get().getSenha());
        return usuarioDTO;
    }

    @Override
    public String cadastrarUsuario(UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes attributes) {
        boolean temErros = validacaoCamposCadastro(usuarioDTO, result);

        if (temErros){
            List<String> listaErros = capturaMensagensErroResult(result);
            attributes.addFlashAttribute(ALERTA_TIPO_ERRO, "Usuário não cadastrado no sistema");
            attributes.addFlashAttribute(ALERTA_TIPO_ERRO, listaErros);
        }else{
            String senhaCriptografada = passwordEncoder.encode(usuarioDTO.getSenha());
            usuarioDTO.setSenha(senhaCriptografada);
            usuarioDTO.setStatus(1);

            salvarUsuario(usuarioDTO);
            attributes.addFlashAttribute(ALERTA_TIPO_SUCESSO, "Usuário salvo com sucesso!");
        }
        return LISTAR;
    }

    private boolean validacaoCamposAlterar(HttpSession session, UsuarioDTO usuarioDTO, List<String> mensagensErro) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) session.getAttribute("usuarioLogado");
        if (!isNomeValido(usuarioDTO.getNome()) || usuarioDTO.getNome().length() < 3){
            mensagensErro.add("O nome deve conter apenas letras e espaços");
        }

        if (existeEmailOutroUsuario(usuarioDTO.getEmail(), usuarioDTO.getId())) {
            mensagensErro.add("O e-mail já está cadastrado. Por favor, escolha outro.");
        }

        if (usuarioLogado.getId().equals(usuarioDTO.getId()) && !Objects.equals(usuarioLogado.getGrupo(), usuarioDTO.getGrupo())){
            mensagensErro.add("O usuário logado não pode alterar o seu próprio grupo");
        }

        return !mensagensErro.isEmpty();
    }

    private boolean existeEmailOutroUsuario(String email, Long id) {
        return usuariosRepository.existsByIdNotAndEmailIgnoreCase(id, email);
    }

    private boolean validacaoCamposCadastro(UsuarioDTO usuarioDTO, BindingResult result) {
        if (!isNomeValido(usuarioDTO.getNome()) || usuarioDTO.getNome().length() < 3){
            result.rejectValue("nome", "nome.invalido", "O nome deve conter apenas letras e espaços");
        }

        if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmacaoSenha())) {
            result.rejectValue("confirmacaoSenha", "senha.mismatch", "As senhas digitadas não são iguais.");
        }

        if (existeEmail(usuarioDTO.getEmail())) {
            result.rejectValue("email", "email.exists", "O e-mail já está cadastrado. Por favor, escolha outro.");
        }

        return result.hasErrors();
    }

    private void salvarUsuario(UsuarioDTO usuarioDTO) {
        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.getSenha());
        usuarioDTO.setSenha(senhaCriptografada);
        usuarioDTO.setStatus(1);

        UsuarioEntity entity = modelMapper.map(usuarioDTO, UsuarioEntity.class);

        usuariosRepository.save(entity);
    }

    private boolean existeEmail(String email) {
        return usuariosRepository.existsByEmail(email);
    }

    @Override
    public List<String> capturaMensagensErroResult(BindingResult result) {
        List<String> mensagensErros = new ArrayList<>();

        // Itera sobre todos os erros de campo
        result.getFieldErrors().forEach(erros -> mensagensErros.add(erros.getDefaultMessage()));

        return mensagensErros;
    }


    private boolean isNomeValido(String nome) {
        return StringUtils.hasText(nome) && nome.matches("^[a-zA-Z\\s]+$");
    }
}
