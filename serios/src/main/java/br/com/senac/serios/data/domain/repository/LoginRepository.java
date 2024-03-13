package br.com.senac.serios.data.domain.repository;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import br.com.senac.serios.dto.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<UsuarioEntity, Long>{
    boolean existsByEmailAndSenha(@Nullable String email, @Nullable String senha);

    UsuarioDTO findByEmailIgnoreCaseAndSenha(@NonNull String email, @NonNull String senha);

    UsuarioDTO findByEmailIgnoreCase(String email);
}
