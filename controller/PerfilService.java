import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    // Método para alterar o perfil
    public String alterarPerfil(Perfil usuarioLogado, int idUsuarioAlterado, String novoNome, String novaSenha, String confirmarSenha) {
        if (usuarioLogado.getId() == idUsuarioAlterado) {
            return "Você não pode alterar o grupo do próprio usuário.";
        }

        Perfil perfilAlterado = perfilRepository.findById(idUsuarioAlterado).orElse(null);
        if (perfilAlterado == null) {
            return "Usuário não encontrado.";
        }

        if (novoNome != null) {
            perfilAlterado.setNome(novoNome);
            perfilRepository.save(perfilAlterado);
            return "Nome alterado com sucesso.";
        }

        if (novaSenha != null && novaSenha.equals(confirmarSenha)) {
            // Atualize a senha
            perfilAlterado.setSenha(encriptarSenha(novaSenha));
            perfilRepository.save(perfilAlterado);
            return "Senha alterada com sucesso.";
        } else if (novaSenha != null && !novaSenha.equals(confirmarSenha)) {
            return "A confirmação da senha não coincide.";
        } else {
            return "Senha não alterada.";
        }
    }

    // Método para encriptar a senha
    private String encriptarSenha(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
