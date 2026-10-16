package com.example.hotel.repository;

import com.example.hotel.entity.PlatoComprado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatoCompradoRepository extends JpaRepository<PlatoComprado, Long> {

    boolean existsByReservaId(Long reservaId);

    boolean existsByTipoPlatoId(Long tipoPlatoId);

    List<PlatoComprado> findAllByReservaId(Long reservaId);

    List<PlatoComprado> findAllByTipoPlatoId(Long tipoPlatoId);
}
