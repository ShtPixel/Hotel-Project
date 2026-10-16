package com.example.hotel.controller;

import com.example.hotel.dto.CreateHabitacionDTO;
import com.example.hotel.dto.HabitacionResponseDTO;
import com.example.hotel.dto.UpdateHabitacionDTO;
import com.example.hotel.service.HabitacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HabitacionResponseDTO create(@Valid @RequestBody CreateHabitacionDTO dto) {
        return habitacionService.create(dto);
    }

    @GetMapping
    public List<HabitacionResponseDTO> findAll() {
        return habitacionService.findAll();
    }

    @GetMapping("/{id}")
    public HabitacionResponseDTO findById(@PathVariable Long id) {
        return habitacionService.findById(id);
    }

    @PutMapping("/{id}")
    public HabitacionResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateHabitacionDTO dto) {
        return habitacionService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        habitacionService.delete(id);
    }
}
