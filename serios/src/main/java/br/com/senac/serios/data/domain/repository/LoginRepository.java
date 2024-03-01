package br.com.senac.serios.data.domain.repository;

import br.com.senac.serios.data.domain.entity.LoginEntity;
import br.com.senac.serios.data.domain.entity.pk.LoginPrimaryKey;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, LoginPrimaryKey>{
}
