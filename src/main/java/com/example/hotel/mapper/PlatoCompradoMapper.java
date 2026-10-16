package com.example.hotel.mapper;

import com.example.hotel.dto.BasePlatoCompradoDTO;
import com.example.hotel.dto.PlatoCompradoResponseDTO;
import com.example.hotel.entity.PlatoComprado;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlatoCompradoMapper {

    public PlatoComprado toEntity(BasePlatoCompradoDTO dto) {
        PlatoComprado platoComprado = new PlatoComprado();
        updateEntity(dto, platoComprado);
        return platoComprado;
    }

    public void updateEntity(BasePlatoCompradoDTO dto, PlatoComprado platoComprado) {
        platoComprado.setValorPagado(dto.getValorPagado());
    }

    public PlatoCompradoResponseDTO toResponseDTO(PlatoComprado platoComprado) {
        return new PlatoCompradoResponseDTO(
                platoComprado.getId(),
                platoComprado.getValorPagado(),
                platoComprado.getReserva().getId(),
                platoComprado.getTipoPlato().getId()
        );
    }

    public List<PlatoCompradoResponseDTO> toResponseDTOList(
            List<PlatoComprado> platosComprados) {
        return platosComprados.stream().map(this::toResponseDTO).toList();
    }
}
