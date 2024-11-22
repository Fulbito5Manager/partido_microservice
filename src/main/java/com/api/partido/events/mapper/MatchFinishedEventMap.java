package com.api.partido.events.mapper;

import com.api.partido.domain.Partido;
import com.f5app.eventpublisher.domain.Event;
import com.f5app.eventpublisher.domain.EventType;
import com.f5app.eventpublisher.mappers.EventMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MatchFinishedEventMap implements EventMap {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Event mapToEvent(Object data) {
        Partido partido = (Partido) data;

        if(!partido.getEstado().equals("TERMINADO"))
            return null;

        String matchFinishedEventData = getPartidoFinalizadoEventData(partido);
        return new Event(
            EventType.MATCH_FINISHED,
            matchFinishedEventData
        );
    }

    private String getPartidoFinalizadoEventData(Partido partidoTerminado) {
        Map<String, Object> eventPayload = new HashMap<>();
        eventPayload.put("match_id", partidoTerminado.getId());
        try {
            return objectMapper.writeValueAsString(eventPayload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
