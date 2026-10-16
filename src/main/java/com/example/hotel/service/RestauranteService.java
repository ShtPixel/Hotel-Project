package com.example.hotel.service;

import com.example.hotel.dto.CreateRestauranteDTO;
import com.example.hotel.dto.RestauranteResponseDTO;
import com.example.hotel.dto.UpdateRestauranteDTO;
import com.example.hotel.entity.Restaurante;
import com.example.hotel.exception.ApplicationException;
import com.example.hotel.exception.ErrorCase;
import com.example.hotel.mapper.RestauranteMapper;
import com.example.hotel.repository.RestauranteRepository;
import com.example.hotel.repository.TipoPlatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final TipoPlatoRepository tipoPlatoRepository;
    private final RestauranteMapper restauranteMapper;

    public RestauranteService(
            RestauranteRepository restauranteRepository,
            TipoPlatoRepository tipoPlatoRepository,
            RestauranteMapper restauranteMapper) {
        this.restauranteRepository = restauranteRepository;
        this.tipoPlatoRepository = tipoPlatoRepository;
        this.restauranteMapper = restauranteMapper;
    }

    public RestauranteResponseDTO create(CreateRestauranteDTO dto) {
        return restauranteMapper.toResponseDTO(
                restauranteRepository.save(restauranteMapper.toEntity(dto))
        );
    }

    @Transactional(readOnly = true)
    public List<RestauranteResponseDTO> findAll() {
        return restauranteMapper.toResponseDTOList(restauranteRepository.findAll());
    }

    @Transactional(readOnly = true)
    public RestauranteResponseDTO findById(Long id) {
        return restauranteMapper.toResponseDTO(findEntityById(id));
    }

    public RestauranteResponseDTO update(Long id, UpdateRestauranteDTO dto) {
        Restaurante restaurante = findEntityById(id);
        restauranteMapper.updateEntity(dto, restaurante);
        return restauranteMapper.toResponseDTO(restauranteRepository.save(restaurante));
    }

    public void delete(Long id) {
        Restaurante restaurante = findEntityById(id);
        if (tipoPlatoRepository.existsByRestauranteId(id)) {
            throw new ApplicationException(
                    ErrorCase.INVALID_OPERATION,
                    "No se puede eliminar un restaurante que tiene tipos de plato"
            );
        }
        restauranteRepository.delete(restaurante);
    }

    private Restaurante findEntityById(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Restaurante no encontrado con id: " + id
                ));
    }
}
