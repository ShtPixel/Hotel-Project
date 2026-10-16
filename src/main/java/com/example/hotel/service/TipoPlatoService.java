package com.example.hotel.service;

import com.example.hotel.dto.CreateTipoPlatoDTO;
import com.example.hotel.dto.TipoPlatoResponseDTO;
import com.example.hotel.dto.UpdateTipoPlatoDTO;
import com.example.hotel.entity.Restaurante;
import com.example.hotel.entity.TipoPlato;
import com.example.hotel.exception.ApplicationException;
import com.example.hotel.exception.ErrorCase;
import com.example.hotel.mapper.TipoPlatoMapper;
import com.example.hotel.repository.PlatoCompradoRepository;
import com.example.hotel.repository.RestauranteRepository;
import com.example.hotel.repository.TipoPlatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoPlatoService {

    private final TipoPlatoRepository tipoPlatoRepository;
    private final RestauranteRepository restauranteRepository;
    private final PlatoCompradoRepository platoCompradoRepository;
    private final TipoPlatoMapper tipoPlatoMapper;

    public TipoPlatoService(
            TipoPlatoRepository tipoPlatoRepository,
            RestauranteRepository restauranteRepository,
            PlatoCompradoRepository platoCompradoRepository,
            TipoPlatoMapper tipoPlatoMapper) {
        this.tipoPlatoRepository = tipoPlatoRepository;
        this.restauranteRepository = restauranteRepository;
        this.platoCompradoRepository = platoCompradoRepository;
        this.tipoPlatoMapper = tipoPlatoMapper;
    }

    public TipoPlatoResponseDTO create(CreateTipoPlatoDTO dto) {
        TipoPlato tipoPlato = tipoPlatoMapper.toEntity(dto);
        tipoPlato.setRestaurante(findRestaurante(dto.getRestauranteId()));
        return tipoPlatoMapper.toResponseDTO(tipoPlatoRepository.save(tipoPlato));
    }

    @Transactional(readOnly = true)
    public List<TipoPlatoResponseDTO> findAll() {
        return tipoPlatoMapper.toResponseDTOList(tipoPlatoRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<TipoPlatoResponseDTO> findByRestauranteId(Long restauranteId) {
        findRestaurante(restauranteId);
        return tipoPlatoMapper.toResponseDTOList(
                tipoPlatoRepository.findAllByRestauranteId(restauranteId)
        );
    }

    @Transactional(readOnly = true)
    public TipoPlatoResponseDTO findById(Long id) {
        return tipoPlatoMapper.toResponseDTO(findEntityById(id));
    }

    public TipoPlatoResponseDTO update(Long id, UpdateTipoPlatoDTO dto) {
        TipoPlato tipoPlato = findEntityById(id);
        tipoPlatoMapper.updateEntity(dto, tipoPlato);
        tipoPlato.setRestaurante(findRestaurante(dto.getRestauranteId()));
        return tipoPlatoMapper.toResponseDTO(tipoPlatoRepository.save(tipoPlato));
    }

    public void delete(Long id) {
        TipoPlato tipoPlato = findEntityById(id);
        if (platoCompradoRepository.existsByTipoPlatoId(id)) {
            throw new ApplicationException(
                    ErrorCase.INVALID_OPERATION,
                    "No se puede eliminar un tipo de plato que ya fue comprado"
            );
        }
        tipoPlatoRepository.delete(tipoPlato);
    }

    private TipoPlato findEntityById(Long id) {
        return tipoPlatoRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Tipo de plato no encontrado con id: " + id
                ));
    }

    private Restaurante findRestaurante(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Restaurante no encontrado con id: " + id
                ));
    }
}
