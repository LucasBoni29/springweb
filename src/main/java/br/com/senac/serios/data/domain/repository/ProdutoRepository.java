/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.senac.serios.data.domain.repository;

import br.com.senac.serios.data.domain.entity.ProdutoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    ProdutoEntity save(ProdutoEntity produto);

    Page<ProdutoEntity> findByNomeContainingIgnoreCase(String keyword, Pageable pageable);

    @Override
    Optional<ProdutoEntity> findById(Long aLong);
}
