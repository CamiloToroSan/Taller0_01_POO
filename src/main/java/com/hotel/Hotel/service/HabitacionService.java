package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.Habitacion;
import com.hotel.Hotel.dto.request.CrearHabitacionRequest;
import com.hotel.Hotel.dto.response.HabitacionResponse;
import com.hotel.Hotel.mapper.HabitacionMapper;
import com.hotel.Hotel.repository.HabitacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    public HabitacionService(HabitacionRepository habitacionRepository,
                             HabitacionMapper habitacionMapper) {
        this.habitacionRepository = habitacionRepository;
        this.habitacionMapper = habitacionMapper;
    }

    @Transactional
    public HabitacionResponse crear(CrearHabitacionRequest request) {
        Habitacion entidad = habitacionMapper.toEntity(request);
        Habitacion guardada = habitacionRepository.save(entidad);
        return habitacionMapper.toResponse(guardada);
    }

    @Transactional(readOnly = true)
    public List<HabitacionResponse> listarTodas() {
        return habitacionMapper.toResponseList(habitacionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorId(UUID id) {
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada"));
        return habitacionMapper.toResponse(habitacion);
    }
}