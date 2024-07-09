package com.api.partido.controller;

import com.api.partido.dto.PartidoUpdateRequestDto;
import com.api.partido.service.PartidoService;
import com.api.partido.domain.Partido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @GetMapping
    public List<Partido> getAllPartidos(){

        return partidoService.getAllPartidos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partido> getPartidoById(@PathVariable Long id){
        Optional<Partido> partido = partidoService.getPartidoById(id);
        return partido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public Partido createPartido(@RequestBody Partido partido){

        return partidoService.savePartido(partido);
    }
    @PatchMapping("/{id}")
    public  ResponseEntity<Partido> parchearPartido(@PathVariable Long id,@RequestBody PartidoUpdateRequestDto partidoUpdateRequestDto){
        Optional<Partido> partido = partidoService.getPartidoById(id);
        if(partido.isPresent()){
            Partido partidoEncontrado = partido.get();
            if(partidoUpdateRequestDto.getCanchaId() != null){
                partidoEncontrado.setCanchaId(partidoUpdateRequestDto.getCanchaId());
            }
            if(partidoUpdateRequestDto.getDate() != null){
                partidoEncontrado.setDate(partidoUpdateRequestDto.getDate());
            }
            if(partidoUpdateRequestDto.getEstado() != null){
                partidoEncontrado.setEstado(partidoUpdateRequestDto.getEstado());
            }
            Partido updatedPartido = partidoService.savePartido(partidoEncontrado);
            return ResponseEntity.ok(updatedPartido);
        } else {
           return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partido> updatePartido(@PathVariable Long id, @RequestBody Partido partidoDetails){
        Optional<Partido> partido = partidoService.getPartidoById(id);
        if(partido.isPresent()){
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartido(@PathVariable Long id){
        Optional<Partido> partido = partidoService.getPartidoById(id);
        if(partido.isPresent()){
            partidoService.deletePartido(id);
            return ResponseEntity.noContent().build();
        } else {
            return  ResponseEntity.notFound().build();
        }
    }
}
