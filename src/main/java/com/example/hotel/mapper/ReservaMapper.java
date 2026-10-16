package com.example.hotel.mapper;

import com.example.hotel.dto.BaseReservaDTO;
import com.example.hotel.dto.ReservaResponseDTO;
import com.example.hotel.entity.Reserva;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservaMapper {

    public Reserva toEntity(BaseReservaDTO dto) {
        Reserva reserva = new Reserva();
        updateEntity(dto, reserva);
        return reserva;
    }

    public void updateEntity(BaseReservaDTO dto, Reserva reserva) {
        reserva.setFechaInicio(dto.getFechaInicio());
        reserva.setFechaFin(dto.getFechaFin());
    }

    public ReservaResponseDTO toResponseDTO(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getFechaInicio(),
                reserva.getFechaFin(),
                reserva.getCliente().getId(),
                reserva.getHabitacion().getId()
        );
    }

    public List<ReservaResponseDTO> toResponseDTOList(List<Reserva> reservas) {
        return reservas.stream().map(this::toResponseDTO).toList();
    }
}
