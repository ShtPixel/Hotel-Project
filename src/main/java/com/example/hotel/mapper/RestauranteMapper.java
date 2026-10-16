package com.example.hotel.mapper;

import com.example.hotel.dto.BaseRestauranteDTO;
import com.example.hotel.dto.RestauranteResponseDTO;
import com.example.hotel.entity.Restaurante;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestauranteMapper {

    public Restaurante toEntity(BaseRestauranteDTO dto) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre(dto.getNombre());
        return restaurante;
    }

    public void updateEntity(BaseRestauranteDTO dto, Restaurante restaurante) {
        restaurante.setNombre(dto.getNombre());
    }

    public RestauranteResponseDTO toResponseDTO(Restaurante restaurante) {
        return new RestauranteResponseDTO(restaurante.getId(), restaurante.getNombre());
    }

    public List<RestauranteResponseDTO> toResponseDTOList(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toResponseDTO).toList();
    }
}
