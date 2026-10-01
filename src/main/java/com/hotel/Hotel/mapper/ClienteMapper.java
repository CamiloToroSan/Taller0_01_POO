package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.domain.Reserva;
import com.hotel.Hotel.dto.request.ActualizarClienteRequest;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.dto.response.ClienteResumenResponse;
import com.hotel.Hotel.dto.response.ReservaItemResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteResponse toResponse(Cliente cliente);

    List<ClienteResponse> toResponseList(List<Cliente> clientes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "penalizaciones", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    Cliente toEntity(CrearClienteRequest request);

    // ---------- Tarea 2 ----------

    @Mapping(target = "totalReservasRealizadas",
            expression = "java(cliente.getReservas().size())")
    @Mapping(target = "montoTotalGastado",
            expression = "java(cliente.getReservas().stream().mapToDouble(r -> r.getCostoTotal()).sum())")
    @Mapping(target = "reservasRecientes",
            source = "reservas",
            qualifiedByName = "toReservaItemList")
    ClienteResumenResponse toResumen(Cliente cliente);

    @Named("toReservaItemList")
    default List<ReservaItemResponse> toReservaItemList(List<Reserva> reservas) {
        return reservas.stream()
                .map(this::toReservaItem)
                .toList();
    }

    @Mapping(target = "idReserva", source = "id")
    @Mapping(target = "numeroHabitacion", source = "habitacion.numero")
    @Mapping(target = "fechaInicio",
            expression = "java(reserva.getPeriodo().fechaInicio().toLocalDate())")
    @Mapping(target = "fechaFin",
            expression = "java(reserva.getPeriodo().fechaFin().toLocalDate())")
    @Mapping(target = "estado",
            expression = "java(reserva.getEstado().name())")
    ReservaItemResponse toReservaItem(Reserva reserva);

    // ---------- Tarea 3: actualización parcial ----------

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "penalizaciones", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    void updateClienteFromDto(ActualizarClienteRequest dto, @MappingTarget Cliente entity);
}