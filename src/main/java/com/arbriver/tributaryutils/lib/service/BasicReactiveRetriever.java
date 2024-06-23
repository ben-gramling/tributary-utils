package com.arbriver.tributaryutils.lib.service;

import com.arbriver.tributaryutils.lib.dal.MatchRepository;
import com.arbriver.tributaryutils.lib.model.*;
import com.arbriver.tributaryutils.lib.model.service.ReactiveRetriever;
import com.fasterxml.jackson.databind.JsonNode;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class BasicReactiveRetriever implements ReactiveRetriever {
    private final Logger _logger = LoggerFactory.getLogger(this.getClass());
    private final BasicRetrieverParser parser;
    private final BasicRestService restService;
    private final MatchRepository matchRepository;

    public BasicReactiveRetriever(
            @Qualifier("parser") BasicRetrieverParser parser,
            @Qualifier("restservice") BasicRestService restService, MatchRepository matchRepository) {
        this.parser = parser;
        this.restService = restService;
        this.matchRepository = matchRepository;
    }

    protected Flux<JsonNode> getEventStream() {
        return restService.getEventStream();
    }

    protected Flux<EventIDMapping> getEventIDMappingStream(Publisher<JsonNode> eventStream) {
        return parser.parseEventResponse(eventStream).flatMap(this::normalizeEventMapping);
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
                .doOnComplete(() -> {
                    _logger.info("Retrieved {} matches and {} positions", eventIDs.size(), positionIDSet.size());
                });
    }

    private Mono<EventIDMapping> normalizeEventMapping(EventIDMapping eventMapping) {
        Match match = eventMapping.match();
        return matchRepository.findSimilarMatch(match.getHomeName(), match.getAwayName())
                .defaultIfEmpty(match)
                .flatMap(existingMatch -> {
            Match temp = new Match();
            temp.setSport(existingMatch.getSport());
            temp.setMatchID(match.getMatchID());
            temp.setHomeName(match.getHomeName());
            temp.setAwayName(match.getAwayName());
            if(!match.getMatchID().equals(existingMatch.getMatchID())) {
                _logger.info("This match ({} vs {}) looks like a duplicate of an existing match ({} vs {}). Will refer to the existing match: {}",
                        match.getHomeName(), match.getAwayName(), existingMatch.getHomeName(), existingMatch.getAwayName(), existingMatch.getMatchID());
                temp.setMatchID(existingMatch.getMatchID());
                temp.setHomeName(existingMatch.getHomeName());
                temp.setAwayName(existingMatch.getAwayName());
            }
            Map<Bookmaker, String> newLinks = new HashMap<>();
            newLinks.putAll(existingMatch.getLinks());
            newLinks.putAll(match.getLinks());
            temp.setLinks(newLinks);
            return Mono.just(new EventIDMapping(eventMapping.eventID(), temp));
        });
    }
}
