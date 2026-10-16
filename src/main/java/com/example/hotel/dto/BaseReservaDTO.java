package com.example.hotel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public abstract class BaseReservaDTO {

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "La habitación es obligatoria")
    private Long habitacionId;
}
