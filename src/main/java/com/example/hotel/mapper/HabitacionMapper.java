package com.example.hotel.mapper;

import com.example.hotel.dto.BaseHabitacionDTO;
import com.example.hotel.dto.HabitacionResponseDTO;
import com.example.hotel.entity.Habitacion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HabitacionMapper {

    public Habitacion toEntity(BaseHabitacionDTO dto) {
        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(dto.getNumero());
        return habitacion;
    }

    public void updateEntity(BaseHabitacionDTO dto, Habitacion habitacion) {
        habitacion.setNumero(dto.getNumero());
    }

    public HabitacionResponseDTO toResponseDTO(Habitacion habitacion) {
        return new HabitacionResponseDTO(habitacion.getId(), habitacion.getNumero());
    }

    public List<HabitacionResponseDTO> toResponseDTOList(List<Habitacion> habitaciones) {
        return habitaciones.stream().map(this::toResponseDTO).toList();
    }
}
