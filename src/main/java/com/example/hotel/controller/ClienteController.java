package com.example.hotel.controller;

import com.example.hotel.dto.ClienteResponseDTO;
import com.example.hotel.dto.CreateClienteDTO;
import com.example.hotel.dto.UpdateClienteDTO;
import com.example.hotel.service.ClienteService;
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
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO create(@Valid @RequestBody CreateClienteDTO dto) {
        return clienteService.create(dto);
    }

    @GetMapping
    public List<ClienteResponseDTO> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO findById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateClienteDTO dto) {
        return clienteService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clienteService.delete(id);
    }
}
