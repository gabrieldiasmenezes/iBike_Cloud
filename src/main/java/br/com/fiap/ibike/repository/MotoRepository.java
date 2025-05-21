package br.com.fiap.ibike.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.ibike.components.StatusMoto;
import br.com.fiap.ibike.model.Moto;

public interface MotoRepository extends JpaRepository<Moto, String>, JpaSpecificationExecutor<Moto> {
    Page<Moto> findByStatusAndPatio(StatusMoto status, String nome, Pageable pageable);

    Page<Moto> findByPatio_Nome(String nome, Pageable pageable);

    Page<Moto> findByStatus(StatusMoto status, Pageable pageable);
}