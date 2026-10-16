package com.example.hotel.service;

import com.example.hotel.dto.CreatePlatoCompradoDTO;
import com.example.hotel.dto.PlatoCompradoResponseDTO;
import com.example.hotel.dto.UpdatePlatoCompradoDTO;
import com.example.hotel.entity.PlatoComprado;
import com.example.hotel.entity.Reserva;
import com.example.hotel.entity.TipoPlato;
import com.example.hotel.exception.ApplicationException;
import com.example.hotel.exception.ErrorCase;
import com.example.hotel.mapper.PlatoCompradoMapper;
import com.example.hotel.repository.PlatoCompradoRepository;
import com.example.hotel.repository.ReservaRepository;
import com.example.hotel.repository.TipoPlatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlatoCompradoService {

    private final PlatoCompradoRepository platoCompradoRepository;
    private final ReservaRepository reservaRepository;
    private final TipoPlatoRepository tipoPlatoRepository;
    private final PlatoCompradoMapper platoCompradoMapper;

    public PlatoCompradoService(
            PlatoCompradoRepository platoCompradoRepository,
            ReservaRepository reservaRepository,
            TipoPlatoRepository tipoPlatoRepository,
            PlatoCompradoMapper platoCompradoMapper) {
        this.platoCompradoRepository = platoCompradoRepository;
        this.reservaRepository = reservaRepository;
        this.tipoPlatoRepository = tipoPlatoRepository;
        this.platoCompradoMapper = platoCompradoMapper;
    }

    public PlatoCompradoResponseDTO create(CreatePlatoCompradoDTO dto) {
        PlatoComprado platoComprado = platoCompradoMapper.toEntity(dto);
        platoComprado.setReserva(findReserva(dto.getReservaId()));
        platoComprado.setTipoPlato(findTipoPlato(dto.getTipoPlatoId()));
        return platoCompradoMapper.toResponseDTO(
                platoCompradoRepository.save(platoComprado)
        );
    }

    @Transactional(readOnly = true)
    public List<PlatoCompradoResponseDTO> findAll() {
        return platoCompradoMapper.toResponseDTOList(platoCompradoRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<PlatoCompradoResponseDTO> findByReservaId(Long reservaId) {
        findReserva(reservaId);
        return platoCompradoMapper.toResponseDTOList(
                platoCompradoRepository.findAllByReservaId(reservaId)
        );
    }

    @Transactional(readOnly = true)
    public List<PlatoCompradoResponseDTO> findByTipoPlatoId(Long tipoPlatoId) {
        findTipoPlato(tipoPlatoId);
        return platoCompradoMapper.toResponseDTOList(
                platoCompradoRepository.findAllByTipoPlatoId(tipoPlatoId)
        );
    }

    @Transactional(readOnly = true)
    public PlatoCompradoResponseDTO findById(Long id) {
        return platoCompradoMapper.toResponseDTO(findEntityById(id));
    }

    public PlatoCompradoResponseDTO update(Long id, UpdatePlatoCompradoDTO dto) {
        PlatoComprado platoComprado = findEntityById(id);
        platoCompradoMapper.updateEntity(dto, platoComprado);
        platoComprado.setReserva(findReserva(dto.getReservaId()));
        platoComprado.setTipoPlato(findTipoPlato(dto.getTipoPlatoId()));
        return platoCompradoMapper.toResponseDTO(
                platoCompradoRepository.save(platoComprado)
        );
    }

    public void delete(Long id) {
        platoCompradoRepository.delete(findEntityById(id));
    }

    private PlatoComprado findEntityById(Long id) {
        return platoCompradoRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Plato comprado no encontrado con id: " + id
                ));
    }

    private Reserva findReserva(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Reserva no encontrada con id: " + id
                ));
    }

    private TipoPlato findTipoPlato(Long id) {
        return tipoPlatoRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Tipo de plato no encontrado con id: " + id
                ));
    }
}
