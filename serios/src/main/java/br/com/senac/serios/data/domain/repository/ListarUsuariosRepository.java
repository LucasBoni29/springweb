package br.com.senac.serios.data.domain.repository;

import br.com.senac.serios.data.domain.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListarUsuariosRepository extends JpaRepository<UsuarioEntity, Long> {
}
