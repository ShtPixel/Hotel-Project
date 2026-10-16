package com.example.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseTipoPlatoDTO {

    @NotBlank(message = "El nombre del tipo de plato es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    @NotNull(message = "El restaurante es obligatorio")
    private Long restauranteId;
}
