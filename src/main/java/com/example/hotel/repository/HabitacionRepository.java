package com.example.hotel.repository;

import com.example.hotel.entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    boolean existsByNumero(String numero);

    boolean existsByNumeroAndIdNot(String numero, Long id);
}
