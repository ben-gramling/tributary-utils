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

import javax.naming.NameNotFoundException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static java.lang.StringTemplate.STR;

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
        return parser.parseEventResponse(eventStream).flatMap(this::enrichEvent);
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

    private Mono<EventIDMapping> enrichEvent(EventIDMapping eventMapping) {
        Match match = eventMapping.match();
        _logger.info("Looking for similar matches to {} vs {} at {}", match.getHomeName(), match.getAwayName(), match.getStartTime());
        //look using the fuzzy strategy
        Mono<Match> potentialMatchOne = matchRepository.findSimilarMatchFuzzy(
                        match.getHomeName(),
                        match.getAwayName(),
                        match.getStartTime().minus(Duration.ofDays(1)),
                        match.getStartTime().plus(Duration.ofDays(1)));

        //look using the one-sided strategy
        Mono<Match> potentialMatchTwo = potentialMatchOne.switchIfEmpty(matchRepository.findSimilarMatchOneSide(
                match.getHomeName(),
                match.getAwayName(),
                match.getStartTime().minus(Duration.ofMinutes(30)),
                match.getStartTime().plus(Duration.ofMinutes(30))
        ));


        return potentialMatchTwo.switchIfEmpty(Mono.error(new NameNotFoundException(
                        STR."Warning, match \{match.getHomeName()} vs \{match.getAwayName()} not found in DB. Discarding.")
                ))
                .onErrorResume(error -> {
                    _logger.warn(error.getMessage());
                    return Mono.empty();
                })
                .flatMap(existingMatch -> {
                    Match temp = new Match();
                    temp.setSport(existingMatch.getSport());
                    temp.setMatchID(existingMatch.getMatchID());
                    temp.setHomeName(existingMatch.getHomeName());
                    temp.setAwayName(existingMatch.getAwayName());
                    Map<Bookmaker, String> newLinks = new HashMap<>();
                    if(existingMatch.getLinks() != null) {
                        newLinks.putAll(existingMatch.getLinks());
                    }
                    newLinks.putAll(match.getLinks());
                    temp.setLinks(newLinks);
                    temp.setNumBooks(temp.getLinks().size());
                    temp.setLeague(existingMatch.getLeague());
                    temp.setStartTime(existingMatch.getStartTime());
                    return Mono.just(new EventIDMapping(eventMapping.eventID(), temp));
                });
    }
}
