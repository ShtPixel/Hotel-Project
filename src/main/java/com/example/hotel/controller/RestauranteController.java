package com.example.hotel.controller;

import com.example.hotel.dto.CreateRestauranteDTO;
import com.example.hotel.dto.RestauranteResponseDTO;
import com.example.hotel.dto.UpdateRestauranteDTO;
import com.example.hotel.service.RestauranteService;
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
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteResponseDTO create(@Valid @RequestBody CreateRestauranteDTO dto) {
        return restauranteService.create(dto);
    }

    @GetMapping
    public List<RestauranteResponseDTO> findAll() {
        return restauranteService.findAll();
    }

    @GetMapping("/{id}")
    public RestauranteResponseDTO findById(@PathVariable Long id) {
        return restauranteService.findById(id);
    }

    @PutMapping("/{id}")
    public RestauranteResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRestauranteDTO dto) {
        return restauranteService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        restauranteService.delete(id);
    }
}
