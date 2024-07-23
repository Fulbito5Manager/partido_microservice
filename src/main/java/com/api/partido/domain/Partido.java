package com.api.partido.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "partido")
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date;

    @Column(nullable = false)
    private Long canchaId;
    @Column(nullable = false)
    private String estado;
    @Column(nullable = false)
    private Long equipoGanadorId;

    public Long getEquipoGanadorId() {
        return equipoGanadorId;
    }

    public void setEquipoGanadorId(Long equipoGanadorId) {
        this.equipoGanadorId = equipoGanadorId;
    }

    public Partido() {
    }

    public Partido(Long id, LocalDateTime date, Long canchaId, String estado) {
        this.id = id;
        this.date = date;
        this.canchaId = canchaId;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getCanchaId() {
        return canchaId;
    }

    public void setCanchaId(Long canchaId) {
        this.canchaId = canchaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
