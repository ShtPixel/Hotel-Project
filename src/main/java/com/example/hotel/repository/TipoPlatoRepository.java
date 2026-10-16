package com.example.hotel.repository;

import com.example.hotel.entity.TipoPlato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoPlatoRepository extends JpaRepository<TipoPlato, Long> {

    boolean existsByRestauranteId(Long restauranteId);

    List<TipoPlato> findAllByRestauranteId(Long restauranteId);
}
