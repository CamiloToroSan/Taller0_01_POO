package com.hotel.Hotel.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false)
    private boolean activo;

    @Column(nullable = false)
    private int penalizaciones;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    protected Cliente() {
    }

    public Cliente(String nombre, String email) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.email = email;
        this.activo = true;
        this.penalizaciones = 0;
    }

    public void registrarPenalizacion() {
        this.penalizaciones++;
        if (this.penalizaciones >= 3) {
            this.activo = false;
        }
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public boolean isActivo() { return activo; }
    public int getPenalizaciones() { return penalizaciones; }
    public List<Reserva> getReservas() { return reservas; }

    // Setters: MapStruct los necesita para escribir sobre la entidad
    // y para poder reconocer id/activo/penalizaciones/reservas y marcarlos como ignore.
    public void setId(UUID id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email) { this.email = email; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public void setPenalizaciones(int penalizaciones) { this.penalizaciones = penalizaciones; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }
}