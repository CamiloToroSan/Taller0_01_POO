package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.dto.request.ActualizarClienteRequest;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.dto.response.ClienteResumenResponse;
import com.hotel.Hotel.mapper.ClienteMapper;
import com.hotel.Hotel.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Transactional
    public ClienteResponse crear(CrearClienteRequest request) {
        Cliente cliente = new Cliente(request.nombre(), request.email());
        Cliente guardado = clienteRepository.save(cliente);
        return clienteMapper.toResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> listarTodos() {
        return clienteMapper.toResponseList(clienteRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ClienteResponse obtenerPorId(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
        return clienteMapper.toResponse(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteResumenResponse obtenerResumen(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
        return clienteMapper.toResumen(cliente);
    }

    @Transactional
    public ClienteResponse actualizarParcial(UUID id, ActualizarClienteRequest request) {
        // 1. Cargar la entidad existente
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));

        // 2. Aplicar solo los campos no nulos
        clienteMapper.updateClienteFromDto(request, cliente);

        // 3. Guardar y devolver el DTO actualizado
        Cliente guardado = clienteRepository.save(cliente);
        return clienteMapper.toResponse(guardado);
    }
}