package com.example.hotel.controller;

import com.example.hotel.dto.CreateTipoPlatoDTO;
import com.example.hotel.dto.TipoPlatoResponseDTO;
import com.example.hotel.dto.UpdateTipoPlatoDTO;
import com.example.hotel.service.TipoPlatoService;
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
@RequestMapping("/api/tipos-plato")
public class TipoPlatoController {

    private final TipoPlatoService tipoPlatoService;

    public TipoPlatoController(TipoPlatoService tipoPlatoService) {
        this.tipoPlatoService = tipoPlatoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoPlatoResponseDTO create(@Valid @RequestBody CreateTipoPlatoDTO dto) {
        return tipoPlatoService.create(dto);
    }

    @GetMapping
    public List<TipoPlatoResponseDTO> findAll() {
        return tipoPlatoService.findAll();
    }

    @GetMapping("/restaurantes/{restauranteId}")
    public List<TipoPlatoResponseDTO> findByRestauranteId(
            @PathVariable Long restauranteId) {
        return tipoPlatoService.findByRestauranteId(restauranteId);
    }

    @GetMapping("/{id}")
    public TipoPlatoResponseDTO findById(@PathVariable Long id) {
        return tipoPlatoService.findById(id);
    }

    @PutMapping("/{id}")
    public TipoPlatoResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTipoPlatoDTO dto) {
        return tipoPlatoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tipoPlatoService.delete(id);
    }
}
