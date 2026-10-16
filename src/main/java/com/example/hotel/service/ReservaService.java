package com.example.hotel.service;

import com.example.hotel.dto.CreateReservaDTO;
import com.example.hotel.dto.BaseReservaDTO;
import com.example.hotel.dto.ReservaResponseDTO;
import com.example.hotel.dto.UpdateReservaDTO;
import com.example.hotel.entity.Cliente;
import com.example.hotel.entity.Habitacion;
import com.example.hotel.entity.Reserva;
import com.example.hotel.exception.ApplicationException;
import com.example.hotel.exception.ErrorCase;
import com.example.hotel.mapper.ReservaMapper;
import com.example.hotel.repository.ClienteRepository;
import com.example.hotel.repository.HabitacionRepository;
import com.example.hotel.repository.PlatoCompradoRepository;
import com.example.hotel.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final HabitacionRepository habitacionRepository;
    private final PlatoCompradoRepository platoCompradoRepository;
    private final ReservaMapper reservaMapper;

    public ReservaService(
            ReservaRepository reservaRepository,
            ClienteRepository clienteRepository,
            HabitacionRepository habitacionRepository,
            PlatoCompradoRepository platoCompradoRepository,
            ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.habitacionRepository = habitacionRepository;
        this.platoCompradoRepository = platoCompradoRepository;
        this.reservaMapper = reservaMapper;
    }

    public ReservaResponseDTO create(CreateReservaDTO dto) {
        validateDates(dto);
        validateAvailability(dto, null);
        Reserva reserva = reservaMapper.toEntity(dto);
        reserva.setCliente(findCliente(dto.getClienteId()));
        reserva.setHabitacion(findHabitacion(dto.getHabitacionId()));
        return reservaMapper.toResponseDTO(reservaRepository.save(reserva));
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> findAll() {
        return reservaMapper.toResponseDTOList(reservaRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> findByClienteId(Long clienteId) {
        findCliente(clienteId);
        return reservaMapper.toResponseDTOList(
                reservaRepository.findAllByClienteId(clienteId)
        );
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> findByHabitacionId(Long habitacionId) {
        findHabitacion(habitacionId);
        return reservaMapper.toResponseDTOList(
                reservaRepository.findAllByHabitacionId(habitacionId)
        );
    }

    @Transactional(readOnly = true)
    public ReservaResponseDTO findById(Long id) {
        return reservaMapper.toResponseDTO(findEntityById(id));
    }

    public ReservaResponseDTO update(Long id, UpdateReservaDTO dto) {
        validateDates(dto);
        Reserva reserva = findEntityById(id);
        validateAvailability(dto, id);
        reservaMapper.updateEntity(dto, reserva);
        reserva.setCliente(findCliente(dto.getClienteId()));
        reserva.setHabitacion(findHabitacion(dto.getHabitacionId()));
        return reservaMapper.toResponseDTO(reservaRepository.save(reserva));
    }

    public void delete(Long id) {
        Reserva reserva = findEntityById(id);
        if (platoCompradoRepository.existsByReservaId(id)) {
            throw new ApplicationException(
                    ErrorCase.INVALID_OPERATION,
                    "No se puede eliminar una reserva que tiene platos comprados"
            );
        }
        reservaRepository.delete(reserva);
    }

    private void validateDates(BaseReservaDTO dto) {
        if (!dto.getFechaFin().isAfter(dto.getFechaInicio())) {
            throw new ApplicationException(
                    ErrorCase.INVALID_OPERATION,
                    "La fecha de fin debe ser posterior a la fecha de inicio"
            );
        }
    }

    private void validateAvailability(BaseReservaDTO dto, Long excludedReservationId) {
        boolean overlaps;
        if (excludedReservationId == null) {
            overlaps = reservaRepository
                    .existsByHabitacionIdAndFechaInicioLessThanAndFechaFinGreaterThan(
                            dto.getHabitacionId(),
                            dto.getFechaFin(),
                            dto.getFechaInicio()
                    );
        } else {
            overlaps = reservaRepository
                    .existsByHabitacionIdAndFechaInicioLessThanAndFechaFinGreaterThanAndIdNot(
                            dto.getHabitacionId(),
                            dto.getFechaFin(),
                            dto.getFechaInicio(),
                            excludedReservationId
                    );
        }

        if (overlaps) {
            throw new ApplicationException(
                    ErrorCase.INVALID_OPERATION,
                    "La habitación ya está reservada durante ese periodo"
            );
        }
    }

    private Reserva findEntityById(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Reserva no encontrada con id: " + id
                ));
    }

    private Cliente findCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Cliente no encontrado con id: " + id
                ));
    }

    private Habitacion findHabitacion(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Habitación no encontrada con id: " + id
                ));
    }
}
