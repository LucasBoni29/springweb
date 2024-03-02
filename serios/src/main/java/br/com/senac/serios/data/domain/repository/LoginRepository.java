package br.com.senac.serios.data.domain.repository;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LoginRepository extends JpaRepository<UsuarioEntity, Long>{
    boolean existsByEmailAndSenha(@Nullable String email, @Nullable String senha);
}
