package com.example.hotel.mapper;

import com.example.hotel.dto.BaseTipoPlatoDTO;
import com.example.hotel.dto.TipoPlatoResponseDTO;
import com.example.hotel.entity.TipoPlato;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TipoPlatoMapper {

    public TipoPlato toEntity(BaseTipoPlatoDTO dto) {
        TipoPlato tipoPlato = new TipoPlato();
        tipoPlato.setNombre(dto.getNombre());
        return tipoPlato;
    }

    public void updateEntity(BaseTipoPlatoDTO dto, TipoPlato tipoPlato) {
        tipoPlato.setNombre(dto.getNombre());
    }

    public TipoPlatoResponseDTO toResponseDTO(TipoPlato tipoPlato) {
        return new TipoPlatoResponseDTO(
                tipoPlato.getId(),
                tipoPlato.getNombre(),
                tipoPlato.getRestaurante().getId()
        );
    }

    public List<TipoPlatoResponseDTO> toResponseDTOList(List<TipoPlato> tiposPlato) {
        return tiposPlato.stream().map(this::toResponseDTO).toList();
    }
}
