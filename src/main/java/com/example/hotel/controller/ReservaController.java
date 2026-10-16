package com.example.hotel.controller;

import com.example.hotel.dto.CreateReservaDTO;
import com.example.hotel.dto.ReservaResponseDTO;
import com.example.hotel.dto.UpdateReservaDTO;
import com.example.hotel.service.ReservaService;
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
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponseDTO create(@Valid @RequestBody CreateReservaDTO dto) {
        return reservaService.create(dto);
    }

    @GetMapping
    public List<ReservaResponseDTO> findAll() {
        return reservaService.findAll();
    }

    @GetMapping("/clientes/{clienteId}")
    public List<ReservaResponseDTO> findByClienteId(@PathVariable Long clienteId) {
        return reservaService.findByClienteId(clienteId);
    }

    @GetMapping("/habitaciones/{habitacionId}")
    public List<ReservaResponseDTO> findByHabitacionId(@PathVariable Long habitacionId) {
        return reservaService.findByHabitacionId(habitacionId);
    }

    @GetMapping("/{id}")
    public ReservaResponseDTO findById(@PathVariable Long id) {
        return reservaService.findById(id);
    }

    @PutMapping("/{id}")
    public ReservaResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateReservaDTO dto) {
        return reservaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        reservaService.delete(id);
    }
}
