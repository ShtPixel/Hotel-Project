package com.example.hotel.service;

import com.example.hotel.dto.CreateHabitacionDTO;
import com.example.hotel.dto.HabitacionResponseDTO;
import com.example.hotel.dto.UpdateHabitacionDTO;
import com.example.hotel.entity.Habitacion;
import com.example.hotel.exception.ApplicationException;
import com.example.hotel.exception.ErrorCase;
import com.example.hotel.mapper.HabitacionMapper;
import com.example.hotel.repository.HabitacionRepository;
import com.example.hotel.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final ReservaRepository reservaRepository;
    private final HabitacionMapper habitacionMapper;

    public HabitacionService(
            HabitacionRepository habitacionRepository,
            ReservaRepository reservaRepository,
            HabitacionMapper habitacionMapper) {
        this.habitacionRepository = habitacionRepository;
        this.reservaRepository = reservaRepository;
        this.habitacionMapper = habitacionMapper;
    }

    public HabitacionResponseDTO create(CreateHabitacionDTO dto) {
        if (habitacionRepository.existsByNumero(dto.getNumero())) {
            throw new ApplicationException(
                    ErrorCase.ALREADY_EXISTS,
                    "Ya existe una habitación con ese número"
            );
        }
        return habitacionMapper.toResponseDTO(
                habitacionRepository.save(habitacionMapper.toEntity(dto))
        );
    }

    @Transactional(readOnly = true)
    public List<HabitacionResponseDTO> findAll() {
        return habitacionMapper.toResponseDTOList(habitacionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public HabitacionResponseDTO findById(Long id) {
        return habitacionMapper.toResponseDTO(findEntityById(id));
    }

    public HabitacionResponseDTO update(Long id, UpdateHabitacionDTO dto) {
        Habitacion habitacion = findEntityById(id);
        if (habitacionRepository.existsByNumeroAndIdNot(dto.getNumero(), id)) {
            throw new ApplicationException(
                    ErrorCase.ALREADY_EXISTS,
                    "Ya existe otra habitación con ese número"
            );
        }
        habitacionMapper.updateEntity(dto, habitacion);
        return habitacionMapper.toResponseDTO(habitacionRepository.save(habitacion));
    }

    public void delete(Long id) {
        Habitacion habitacion = findEntityById(id);
        if (reservaRepository.existsByHabitacionId(id)) {
            throw new ApplicationException(
                    ErrorCase.INVALID_OPERATION,
                    "No se puede eliminar una habitación con reservas"
            );
        }
        habitacionRepository.delete(habitacion);
    }

    private Habitacion findEntityById(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Habitación no encontrada con id: " + id
                ));
    }
}
