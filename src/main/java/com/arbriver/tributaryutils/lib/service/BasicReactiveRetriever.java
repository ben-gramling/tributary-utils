package com.arbriver.tributaryutils.lib.service;

import com.arbriver.tributaryutils.lib.model.EventIDMapping;
import com.arbriver.tributaryutils.lib.model.MatchMarketsMapping;
import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import com.arbriver.tributaryutils.lib.model.service.ReactiveRetriever;
import com.fasterxml.jackson.databind.JsonNode;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashSet;

@Service
public class BasicReactiveRetriever implements ReactiveRetriever {
    private final Logger _logger = LoggerFactory.getLogger(this.getClass());
    private final BasicRetrieverParser parser;
    private final BasicRestService restService;

    public BasicReactiveRetriever(
            @Qualifier("parser") BasicRetrieverParser parser,
            @Qualifier("restservice") BasicRestService restService) {
        this.parser = parser;
        this.restService = restService;
    }

    protected Flux<JsonNode> getEventStream() {
        return restService.getEventStream();
    }

    protected Flux<EventIDMapping> getEventIDMappingStream(Publisher<JsonNode> eventStream) {
        return parser.parseEventResponse(eventStream);
    }

    protected Flux<MatchMarketsMapping> getMarketsMapping(Publisher<EventIDMapping> eventIDMappingStream) {
        return restService.getMarketsStream(eventIDMappingStream);
    }

    @Override
    public Flux<MatchUpdate> startRetrieving() {
        HashSet<String> eventIDs = new HashSet<>();
        HashSet<String> positionIDSet = new HashSet<>();
        //get event ids
        Flux<JsonNode> jEvents = getEventStream();

        //get mapping of event ids to matches
        Flux<EventIDMapping> eventMappings = getEventIDMappingStream(jEvents);

        //get mapping of matches to jsonnode (json element where positions are stored)
        Flux<MatchMarketsMapping> marketsMapping = getMarketsMapping(eventMappings);

        //parse json nodes into positions
        return parser.parseMarketsResponse(marketsMapping)
                .doOnNext(m -> {
                    eventIDs.add(m.match().getMatchID());
                    m.positions().forEach(position -> positionIDSet.add(position.getBetId()));
                })
                .doOnComplete(() -> _logger.info("Processed {} matches and {} positions", eventIDs.size(), positionIDSet.size()));
    }
}
