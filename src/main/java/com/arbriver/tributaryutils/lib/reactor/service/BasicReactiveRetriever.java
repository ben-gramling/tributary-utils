package com.arbriver.tributaryutils.lib.reactor.service;

import com.arbriver.tributaryutils.lib.model.EventIDMapping;
import com.arbriver.tributaryutils.lib.model.MatchMarketsMapping;
import com.arbriver.tributaryutils.lib.model.MatchUpdate;
import com.arbriver.tributaryutils.lib.reactor.model.ReactiveRetriever;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Override
    public Flux<MatchUpdate> startRetrieving() {
        HashSet<String> eventIDs = new HashSet<>();
        HashSet<String> positionIDSet = new HashSet<>();
        Flux<JsonNode> jEvents = restService.getEventStream();
        Flux<EventIDMapping> eventMappings = parser.parseEventResponse(jEvents);
        Flux<MatchMarketsMapping> marketsMapping = restService.getMarketsStream(eventMappings);
        return parser.parseMarketsResponse(marketsMapping)
                .doOnNext(m -> {
                    eventIDs.add(m.match().getMatchID());
                    m.positions().forEach(position -> positionIDSet.add(position.getBetId()));
                })
                .doOnComplete(() -> _logger.info("Processed {} matches and {} positions", eventIDs.size(), positionIDSet.size()));
    }
}
