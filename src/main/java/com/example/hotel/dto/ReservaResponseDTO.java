package com.example.hotel.dto;

import java.time.LocalDate;

public record ReservaResponseDTO(
        Long id,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Long clienteId,
        Long habitacionId
) {
}
