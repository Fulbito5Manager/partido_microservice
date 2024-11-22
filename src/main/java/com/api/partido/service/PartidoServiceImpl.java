package com.api.partido.service;
import com.api.partido.dto.PartidoUpdateRequestDto;
import com.api.partido.repository.PartidoRepository;
import com.api.partido.domain.Partido;
import com.f5app.eventpublisher.aspect.eventproducer.annotation.EventProducer;
import com.f5app.eventpublisher.domain.EventType;
import com.f5app.eventpublisher.domain.Topic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartidoServiceImpl implements PartidoService {

    private final PartidoRepository partidoRepository;

    public PartidoServiceImpl(PartidoRepository partidoRepository) {
        this.partidoRepository = partidoRepository;
    }

    public List<Partido> getAllPartidos(){
        return partidoRepository.findAll();
    }

    public Optional<Partido> getPartidoById(Long id){
        return partidoRepository.findById(id);
    }

    public Partido savePartido(Partido partido){
        return partidoRepository.save(partido);
    }

    @EventProducer(topic = Topic.MATCH_SERVICE_TOPIC, eventType = EventType.MATCH_FINISHED)
    public Partido updatePartido(Long partidoId, PartidoUpdateRequestDto partidoUpdateRequestDto){
        Optional<Partido> partido = getPartidoById(partidoId);
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
        return savePartido(partidoEncontrado);
    }

    public void deletePartido(Long id){
        partidoRepository.deleteById(id);
    }

}
