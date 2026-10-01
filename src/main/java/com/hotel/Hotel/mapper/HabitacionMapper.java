package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Habitacion;
import com.hotel.Hotel.domain.HabitacionEstandar;
import com.hotel.Hotel.domain.SuitePresidencial;
import com.hotel.Hotel.dto.request.CrearHabitacionEstandarRequest;
import com.hotel.Hotel.dto.request.CrearHabitacionRequest;
import com.hotel.Hotel.dto.request.CrearSuitePresidencialRequest;
import com.hotel.Hotel.dto.response.HabitacionEstandarResponse;
import com.hotel.Hotel.dto.response.HabitacionResponse;
import com.hotel.Hotel.dto.response.SuitePresidencialResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitacionMapper {



    default HabitacionResponse toResponse(Habitacion habitacion) {
        if (habitacion instanceof HabitacionEstandar estandar) {
            return toResponse(estandar);
        } else if (habitacion instanceof SuitePresidencial suite) {
            return toResponse(suite);
        }
        throw new IllegalArgumentException("Tipo de habitación no soportado");
    }

    @Mapping(target = "estado", expression = "java(habitacion.getEstado().name())")
    HabitacionEstandarResponse toResponse(HabitacionEstandar habitacion);

    @Mapping(target = "estado", expression = "java(habitacion.getEstado().name())")
    SuitePresidencialResponse toResponse(SuitePresidencial habitacion);

    default List<HabitacionResponse> toResponseList(List<Habitacion> habitaciones) {
        return habitaciones.stream()
                .map(this::toResponse)
                .toList();
    }



    default Habitacion toEntity(CrearHabitacionRequest request) {
        if (request instanceof CrearHabitacionEstandarRequest estandar) {
            return toEntity(estandar);
        } else if (request instanceof CrearSuitePresidencialRequest suite) {
            return toEntity(suite);
        }
        throw new IllegalArgumentException("Tipo de request no soportado");
    }

    HabitacionEstandar toEntity(CrearHabitacionEstandarRequest request);

    SuitePresidencial toEntity(CrearSuitePresidencialRequest request);
}