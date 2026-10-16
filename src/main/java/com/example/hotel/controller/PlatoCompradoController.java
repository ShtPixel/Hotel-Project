package com.example.hotel.controller;

import com.example.hotel.dto.CreatePlatoCompradoDTO;
import com.example.hotel.dto.PlatoCompradoResponseDTO;
import com.example.hotel.dto.UpdatePlatoCompradoDTO;
import com.example.hotel.service.PlatoCompradoService;
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
@RequestMapping("/api/platos-comprados")
public class PlatoCompradoController {

    private final PlatoCompradoService platoCompradoService;

    public PlatoCompradoController(PlatoCompradoService platoCompradoService) {
        this.platoCompradoService = platoCompradoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlatoCompradoResponseDTO create(
            @Valid @RequestBody CreatePlatoCompradoDTO dto) {
        return platoCompradoService.create(dto);
    }

    @GetMapping
    public List<PlatoCompradoResponseDTO> findAll() {
        return platoCompradoService.findAll();
    }

    @GetMapping("/reservas/{reservaId}")
    public List<PlatoCompradoResponseDTO> findByReservaId(
            @PathVariable Long reservaId) {
        return platoCompradoService.findByReservaId(reservaId);
    }

    @GetMapping("/tipos-plato/{tipoPlatoId}")
    public List<PlatoCompradoResponseDTO> findByTipoPlatoId(
            @PathVariable Long tipoPlatoId) {
        return platoCompradoService.findByTipoPlatoId(tipoPlatoId);
    }

    @GetMapping("/{id}")
    public PlatoCompradoResponseDTO findById(@PathVariable Long id) {
        return platoCompradoService.findById(id);
    }

    @PutMapping("/{id}")
    public PlatoCompradoResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePlatoCompradoDTO dto) {
        return platoCompradoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        platoCompradoService.delete(id);
    }
}
