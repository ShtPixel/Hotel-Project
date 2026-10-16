package com.example.hotel.dto;

import java.math.BigDecimal;

public record PlatoCompradoResponseDTO(
        Long id,
        BigDecimal valorPagado,
        Long reservaId,
        Long tipoPlatoId
) {
}
