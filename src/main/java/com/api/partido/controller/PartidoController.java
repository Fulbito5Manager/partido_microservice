package com.api.partido.controller;

import com.api.partido.dto.PartidoUpdateRequestDto;
import com.api.partido.service.PartidoServiceImpl;
import com.api.partido.domain.Partido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/partidos")
@Tag(name = "Partido Management System", description = "Operations pertaining to team in Partido Management System")
public class PartidoController {

    @Autowired
    private PartidoServiceImpl partidoService;

    @Operation(summary = "Devuelve una lista de Partidos con todos los Partidos")
    @GetMapping
    public List<Partido> getAllPartidos() {

        return partidoService.getAllPartidos();
    }

    @Operation(summary = "Devuelve un Partido con un id de partido particular")
    @GetMapping("/{id}")
    public ResponseEntity<Partido> getPartidoById(
            @Parameter(description = "ID del partido a devolver", required = true)
            @PathVariable Long id) {
        Optional<Partido> partido = partidoService.getPartidoById(id);
        return partido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Operation(summary = "Crea un nuevo Partido")
    @PostMapping
    public Partido createPartido(
            @Parameter(description = "Objeto partido a crear", required = true)
            @RequestBody Partido partido) {

        return partidoService.savePartido(partido);
    }

    @Operation(summary = "Devuelve un Partido con un atributo actualizado")
    @PatchMapping("/{id}")
    public ResponseEntity<Partido> actualizarPartidoById(
            @Parameter(description = "ID del partido a parchear", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto Request para parchear un partido", required = true)
            @RequestBody PartidoUpdateRequestDto partidoUpdateRequestDto) {
        Optional<Partido> partido = partidoService.getPartidoById(id);
        if (partido.isPresent()) {
            Partido partidoEncontrado = partido.get();
            if (partidoUpdateRequestDto.getCanchaId() != null) {
                partidoEncontrado.setCanchaId(partidoUpdateRequestDto.getCanchaId());
            }
            if (partidoUpdateRequestDto.getDate() != null) {
                partidoEncontrado.setDate(partidoUpdateRequestDto.getDate());
            }
            if (partidoUpdateRequestDto.getEstado() != null) {
                partidoEncontrado.setEstado(partidoUpdateRequestDto.getEstado());
            }
            Partido updatedPartido = partidoService.savePartido(partidoEncontrado);
            return ResponseEntity.ok(updatedPartido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Devuelve un Partido con todos atributos actualizados")
    @PutMapping("/{id}")
    public ResponseEntity<Partido> updatePartido(
            @Parameter(description = "ID del partido a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto de partido actualizado", required = true)
            @RequestBody Partido partidoDetails) {
        Optional<Partido> partido = partidoService.getPartidoById(id);
        if (partido.isPresent()) {
            Partido existingPartido = partido.get();
            existingPartido.setDate(partidoDetails.getDate());
            existingPartido.setCanchaId(partidoDetails.getCanchaId());
            existingPartido.setEstado(partidoDetails.getEstado());
            Partido updatedPartido = partidoService.savePartido(existingPartido);
            return ResponseEntity.ok(updatedPartido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Borra un partido con un id en particular")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartido(
            @Parameter(description = "ID del partido a borrar", required = true)
            @PathVariable Long id) {
        Optional<Partido> partido = partidoService.getPartidoById(id);
        if (partido.isPresent()) {
            partidoService.deletePartido(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
