package com.example.hotel.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public abstract class BasePlatoCompradoDTO {

    @NotNull(message = "El valor pagado es obligatorio")
    @DecimalMin(value = "0.0", message = "El valor pagado no puede ser negativo")
    private BigDecimal valorPagado;

    @NotNull(message = "La reserva es obligatoria")
    private Long reservaId;

    @NotNull(message = "El tipo de plato es obligatorio")
    private Long tipoPlatoId;
}
