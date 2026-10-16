package com.example.hotel.service;

import com.example.hotel.dto.ClienteResponseDTO;
import com.example.hotel.dto.CreateClienteDTO;
import com.example.hotel.dto.UpdateClienteDTO;
import com.example.hotel.entity.Cliente;
import com.example.hotel.exception.ApplicationException;
import com.example.hotel.exception.ErrorCase;
import com.example.hotel.mapper.ClienteMapper;
import com.example.hotel.repository.ClienteRepository;
import com.example.hotel.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ReservaRepository reservaRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(
            ClienteRepository clienteRepository,
            ReservaRepository reservaRepository,
            ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.reservaRepository = reservaRepository;
        this.clienteMapper = clienteMapper;
    }

    public ClienteResponseDTO create(CreateClienteDTO dto) {
        return clienteMapper.toResponseDTO(
                clienteRepository.save(clienteMapper.toEntity(dto))
        );
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findAll() {
        return clienteMapper.toResponseDTOList(clienteRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO findById(Long id) {
        return clienteMapper.toResponseDTO(findEntityById(id));
    }

    public ClienteResponseDTO update(Long id, UpdateClienteDTO dto) {
        Cliente cliente = findEntityById(id);
        clienteMapper.updateEntity(dto, cliente);
        return clienteMapper.toResponseDTO(clienteRepository.save(cliente));
    }

    public void delete(Long id) {
        Cliente cliente = findEntityById(id);
        if (reservaRepository.existsByClienteId(id)) {
            throw new ApplicationException(
                    ErrorCase.INVALID_OPERATION,
                    "No se puede eliminar un cliente con reservas"
            );
        }
        clienteRepository.delete(cliente);
    }

    private Cliente findEntityById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Cliente no encontrado con id: " + id
                ));
    }
}
