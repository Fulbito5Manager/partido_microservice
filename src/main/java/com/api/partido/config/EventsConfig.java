package com.api.partido.config;

import com.api.partido.events.mapper.MatchFinishedEventMap;
import com.f5app.eventpublisher.domain.EventType;
import com.f5app.eventpublisher.mappers.EventMap;
import com.f5app.eventpublisher.service.impl.EventMessageMapFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EventsConfig {

    private final MatchFinishedEventMap matchFinishedEventMap;

    public EventsConfig(MatchFinishedEventMap matchFinishedEventMap) {
        this.matchFinishedEventMap = matchFinishedEventMap;
    }

    @Bean
    public EventMessageMapFactory eventMessageMapFactory() {
        Map<EventType, EventMap> mappers = new HashMap<>();
        mappers.put(EventType.MATCH_FINISHED, matchFinishedEventMap);
        return new EventMessageMapFactory(mappers);
    }
}
