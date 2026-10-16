package com.example.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseHabitacionDTO {

    @NotBlank(message = "El número de habitación es obligatorio")
    @Size(max = 20, message = "El número no puede superar 20 caracteres")
    private String numero;
}
