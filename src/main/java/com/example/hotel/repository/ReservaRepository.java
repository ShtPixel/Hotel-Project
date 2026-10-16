package com.example.hotel.repository;

import com.example.hotel.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsByClienteId(Long clienteId);

    boolean existsByHabitacionId(Long habitacionId);

    List<Reserva> findAllByClienteId(Long clienteId);

    List<Reserva> findAllByHabitacionId(Long habitacionId);

    boolean existsByHabitacionIdAndFechaInicioLessThanAndFechaFinGreaterThan(
            Long habitacionId,
            LocalDate fechaFin,
            LocalDate fechaInicio
    );

    boolean existsByHabitacionIdAndFechaInicioLessThanAndFechaFinGreaterThanAndIdNot(
            Long habitacionId,
            LocalDate fechaFin,
            LocalDate fechaInicio,
            Long id
    );
}
