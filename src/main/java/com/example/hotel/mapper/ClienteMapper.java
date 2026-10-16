package com.example.hotel.mapper;

import com.example.hotel.dto.BaseClienteDTO;
import com.example.hotel.dto.ClienteResponseDTO;
import com.example.hotel.entity.Cliente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapper {

    public Cliente toEntity(BaseClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        return cliente;
    }

    public void updateEntity(BaseClienteDTO dto, Cliente cliente) {
        cliente.setNombre(dto.getNombre());
    }

    public ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(cliente.getId(), cliente.getNombre());
    }

    public List<ClienteResponseDTO> toResponseDTOList(List<Cliente> clientes) {
        return clientes.stream().map(this::toResponseDTO).toList();
    }
}
