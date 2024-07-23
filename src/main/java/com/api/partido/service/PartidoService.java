package com.api.partido.service;

import com.api.partido.domain.Partido;
import com.api.partido.dto.PartidoUpdateRequestDto;

import java.util.List;
import java.util.Optional;

public interface PartidoService {
    List<Partido> getAllPartidos();
    Optional<Partido> getPartidoById(Long id);
    Partido savePartido(Partido partido);
    void deletePartido(Long id);
}
