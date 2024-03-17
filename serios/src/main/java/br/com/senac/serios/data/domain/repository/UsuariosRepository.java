package br.com.senac.serios.data.domain.repository;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuarioEntity, Long> {
    List<UsuarioEntity> findByNomeContaining(@NonNull String nome);

    boolean existsByEmail(String email);

    boolean existsByIdNotAndEmailIgnoreCase(Long id, String email);
}
