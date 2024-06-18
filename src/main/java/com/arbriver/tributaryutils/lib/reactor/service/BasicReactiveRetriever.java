package com.arbriver.tributaryutils.lib.reactor.service;

import com.arbriver.tributaryutils.lib.model.EventIDMapping;
import com.arbriver.tributaryutils.lib.model.MatchMarketsMapping;
import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import com.arbriver.tributaryutils.lib.reactor.model.ReactiveRetriever;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BasicReactiveRetriever implements ReactiveRetriever {
    private final BasicRetrieverParser parser;
    private final BasicRestService restService;

    public BasicReactiveRetriever(
            @Qualifier("parser") BasicRetrieverParser parser,
            @Qualifier("restservice") BasicRestService restService) {
        this.parser = parser;
        this.restService = restService;
    }

    @Override
    public Flux<MatchUpdate> startRetrieving() {
        Flux<JsonNode> jEvents = restService.getEventStream();
        Flux<EventIDMapping> eventMappings = parser.parseEventResponse(jEvents);
        Flux<MatchMarketsMapping> marketsMapping = restService.getMarketsStream(eventMappings);
        return parser.parseMarketsResponse(marketsMapping);
    }
}
